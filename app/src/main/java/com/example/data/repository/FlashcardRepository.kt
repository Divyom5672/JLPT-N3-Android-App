package com.example.data.repository

import com.example.data.local.FlashcardDao
import com.example.data.model.FlashcardEntity
import com.example.data.seed.InitialData
import kotlinx.coroutines.flow.Flow

class FlashcardRepository(private val dao: FlashcardDao) {

    val allFlashcards: Flow<List<FlashcardEntity>> = dao.getAllFlashcards()

    suspend fun ensureDataInitialized() {
        if (dao.getCount() == 0) {
            dao.insertFlashcards(InitialData.INITIAL_FLASHCARDS)
            dao.insertFlashcards(InitialData.INITIAL_VOCAB_FLASHCARDS)
        } else {
            // Check if vocabulary cards are seeded
            if (dao.getFlashcardsByCategory("v_greetings").hashCode() == 0) {
                dao.insertFlashcards(InitialData.INITIAL_VOCAB_FLASHCARDS)
            }
        }
        // Always clean up any duplicate entries and merge adjectives in DB
        dao.deleteDuplicateFlashcards()
        dao.mergeAdjectives()
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
