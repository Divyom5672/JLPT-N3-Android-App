package com.example.data.repository

import com.example.data.local.FlashcardDao
import com.example.data.model.FlashcardEntity
import com.example.data.seed.InitialData
import kotlinx.coroutines.flow.Flow

class FlashcardRepository(private val dao: FlashcardDao) {

    val allFlashcards: Flow<List<FlashcardEntity>> = dao.getAllFlashcards()

    suspend fun ensureDataInitialized() {
        if (dao.getCount() < 1900) {
            dao.clearAll()
            dao.insertFlashcards(InitialData.INITIAL_FLASHCARDS)
        }
    }

    fun getFlashcardsByCategory(categoryId: String): Flow<List<FlashcardEntity>> {
        return dao.getFlashcardsByCategory(categoryId)
    }

    suspend fun markMastered(id: Int, currentTimesReviewed: Int) {
        dao.updateStatus(
            id = id,
            status = "MASTERED",
            timesReviewed = currentTimesReviewed + 1,
            lastReviewedTime = System.currentTimeMillis()
        )
    }

    suspend fun markReview(id: Int, currentTimesReviewed: Int) {
        dao.updateStatus(
            id = id,
            status = "REVIEW",
            timesReviewed = currentTimesReviewed + 1,
            lastReviewedTime = System.currentTimeMillis()
        )
    }

    suspend fun toggleBookmark(id: Int, isBookmarked: Boolean) {
        dao.toggleBookmark(id, isBookmarked)
    }

    suspend fun addFlashcard(card: FlashcardEntity): Long {
        return dao.insertFlashcard(card)
    }

    suspend fun deleteFlashcard(id: Int) {
        dao.deleteFlashcard(id)
    }

    suspend fun resetAllStatus() {
        dao.resetAllStatus()
    }
}
