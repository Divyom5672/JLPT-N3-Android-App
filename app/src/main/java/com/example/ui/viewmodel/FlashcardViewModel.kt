package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.Category
import com.example.data.model.FlashcardEntity
import com.example.data.model.FlashcardFilter
import com.example.data.repository.FlashcardRepository
import com.example.data.seed.InitialData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SwipeActionRecord(
    val card: FlashcardEntity,
    val previousStatus: String,
    val previousIndex: Int
)

data class FlashcardUiState(
    val allCards: List<FlashcardEntity> = emptyList(),
    val filteredCards: List<FlashcardEntity> = emptyList(),
    val deckCards: List<FlashcardEntity> = emptyList(),
    val currentDeckIndex: Int = 0,
    val categories: List<Category> = InitialData.KANJI_CATEGORIES,
    val selectedCategory: String? = null,
    val searchQuery: String = "",
    val activeFilter: FlashcardFilter = FlashcardFilter.ALL,
    val canUndo: Boolean = false,
    val isLoading: Boolean = true
)

private data class FilterParams(
    val cards: List<FlashcardEntity>,
    val selectedCategory: String?,
    val searchQuery: String,
    val activeFilter: FlashcardFilter
)

private data class DeckParams(
    val currentDeckIndex: Int,
    val canUndo: Boolean,
    val shuffleSeed: Long
)

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FlashcardRepository

    private val _selectedCategory = MutableStateFlow<String?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _activeFilter = MutableStateFlow(FlashcardFilter.ALL)
    private val _currentDeckIndex = MutableStateFlow(0)
    private val _shuffleSeed = MutableStateFlow(System.currentTimeMillis())

    private val undoStack = mutableListOf<SwipeActionRecord>()
    private val _canUndo = MutableStateFlow(false)

    init {
        val db = AppDatabase.getDatabase(application)
        repository = FlashcardRepository(db.flashcardDao())

        viewModelScope.launch {
            repository.ensureDataInitialized()
        }
    }

    private val filterParamsFlow = combine(
        repository.allFlashcards,
        _selectedCategory,
        _searchQuery,
        _activeFilter
    ) { cards, selectedCategory, searchQuery, activeFilter ->
        FilterParams(cards, selectedCategory, searchQuery, activeFilter)
    }

    private val deckParamsFlow = combine(
        _currentDeckIndex,
        _canUndo,
        _shuffleSeed
    ) { deckIndex, canUndo, shuffleSeed ->
        DeckParams(deckIndex, canUndo, shuffleSeed)
    }

    val uiState: StateFlow<FlashcardUiState> = combine(
        filterParamsFlow,
        deckParamsFlow
    ) { filterParams, deckParams ->

        val cards = filterParams.cards
        val selectedCategory = filterParams.selectedCategory
        val searchQuery = filterParams.searchQuery
        val activeFilter = filterParams.activeFilter

        val categoryFiltered = if (selectedCategory.isNullOrBlank()) {
            cards
        } else if (selectedCategory == "custom") {
            cards.filter { it.isCustom || it.categoryId == "custom" }
        } else {
            cards.filter { it.categoryId == selectedCategory }
        }

        val searchFiltered = if (searchQuery.isBlank()) {
            categoryFiltered
        } else {
            val query = searchQuery.trim().lowercase()
            categoryFiltered.filter { card ->
                card.kanji.lowercase().contains(query) ||
                card.hiragana.lowercase().contains(query) ||
                card.meaning.lowercase().contains(query)
            }
        }

        val finalFiltered = when (activeFilter) {
            FlashcardFilter.ALL -> searchFiltered
            FlashcardFilter.NEW -> searchFiltered.filter { it.status == "NEW" }
            FlashcardFilter.REVIEW -> searchFiltered.filter { it.status == "REVIEW" }
            FlashcardFilter.MASTERED -> searchFiltered.filter { it.status == "MASTERED" }
            FlashcardFilter.BOOKMARKED -> searchFiltered.filter { it.isBookmarked }
        }

        val deck = categoryFiltered.shuffled(kotlin.random.Random(deckParams.shuffleSeed))

        FlashcardUiState(
            allCards = cards,
            filteredCards = finalFiltered,
            deckCards = deck,
            currentDeckIndex = deckParams.currentDeckIndex.coerceIn(0, deck.size),
            categories = InitialData.KANJI_CATEGORIES,
            selectedCategory = selectedCategory,
            searchQuery = searchQuery,
            activeFilter = activeFilter,
            canUndo = deckParams.canUndo,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FlashcardUiState()
    )

    fun selectCategory(categoryId: String?) {
        _selectedCategory.value = categoryId
        _shuffleSeed.value = System.currentTimeMillis()
        _currentDeckIndex.value = 0
        undoStack.clear()
        _canUndo.value = false
    }

    fun randomizeDeck() {
        _shuffleSeed.value = System.currentTimeMillis()
        _currentDeckIndex.value = 0
        undoStack.clear()
        _canUndo.value = false
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(filter: FlashcardFilter) {
        _activeFilter.value = filter
    }

    fun shuffleDeck() {
        randomizeDeck()
    }

    fun swipeRightMastered(card: FlashcardEntity) {
        viewModelScope.launch {
            val oldIndex = _currentDeckIndex.value
            undoStack.add(SwipeActionRecord(card, card.status, oldIndex))
            _canUndo.value = true

            repository.markMastered(card.id, card.timesReviewed)
            _currentDeckIndex.value = oldIndex + 1
        }
    }

    fun swipeLeftReview(card: FlashcardEntity) {
        viewModelScope.launch {
            val oldIndex = _currentDeckIndex.value
            undoStack.add(SwipeActionRecord(card, card.status, oldIndex))
            _canUndo.value = true

            repository.markReview(card.id, card.timesReviewed)
            _currentDeckIndex.value = oldIndex + 1
        }
    }

    fun undoSwipe() {
        if (undoStack.isNotEmpty()) {
            val lastRecord = undoStack.removeAt(undoStack.size - 1)
            viewModelScope.launch {
                when (lastRecord.previousStatus) {
                    "MASTERED" -> repository.markMastered(lastRecord.card.id, (lastRecord.card.timesReviewed - 1).coerceAtLeast(0))
                    "REVIEW" -> repository.markReview(lastRecord.card.id, (lastRecord.card.timesReviewed - 1).coerceAtLeast(0))
                    else -> repository.markReview(lastRecord.card.id, 0)
                }
                _currentDeckIndex.value = lastRecord.previousIndex
                _canUndo.value = undoStack.isNotEmpty()
            }
        }
    }

    fun toggleBookmark(card: FlashcardEntity) {
        viewModelScope.launch {
            repository.toggleBookmark(card.id, !card.isBookmarked)
        }
    }

    fun resetDeckProgress() {
        viewModelScope.launch {
            repository.resetAllStatus()
            _currentDeckIndex.value = 0
            undoStack.clear()
            _canUndo.value = false
        }
    }

    fun addCustomCard(kanji: String, hiragana: String, meaning: String, categoryId: String) {
        viewModelScope.launch {
            val newCard = FlashcardEntity(
                categoryId = categoryId,
                kanji = kanji.trim(),
                hiragana = hiragana.trim(),
                meaning = meaning.trim(),
                isCustom = true
            )
            repository.addFlashcard(newCard)
        }
    }
}
