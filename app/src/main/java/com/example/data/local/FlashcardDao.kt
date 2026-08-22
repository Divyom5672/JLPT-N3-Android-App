package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.FlashcardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM flashcards ORDER BY id ASC")
    fun getAllFlashcards(): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE categoryId = :categoryId ORDER BY id ASC")
    fun getFlashcardsByCategory(categoryId: String): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE id = :id LIMIT 1")
    suspend fun getFlashcardById(id: Int): FlashcardEntity?

    @Query("SELECT COUNT(*) FROM flashcards")
    suspend fun getCount(): Int

    @Query("DELETE FROM flashcards WHERE id NOT IN (SELECT MIN(id) FROM flashcards GROUP BY categoryId, kanji)")
    suspend fun deleteDuplicateFlashcards()

    @Query("DELETE FROM flashcards")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(flashcards: List<FlashcardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: FlashcardEntity): Long

    @Update
    suspend fun updateFlashcard(flashcard: FlashcardEntity)

    @Query("UPDATE flashcards SET status = :status, timesReviewed = :timesReviewed, lastReviewedTime = :lastReviewedTime WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String, timesReviewed: Int, lastReviewedTime: Long)

    @Query("UPDATE flashcards SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun toggleBookmark(id: Int, isBookmarked: Boolean)

    @Query("DELETE FROM flashcards WHERE id = :id")
    suspend fun deleteFlashcard(id: Int)

    @Query("UPDATE flashcards SET status = 'NEW', timesReviewed = 0, lastReviewedTime = 0")
    suspend fun resetAllStatus()

    @Query("UPDATE flashcards SET categoryId = 'adj' WHERE categoryId IN ('iadj', 'nadj')")
    suspend fun mergeAdjectives()
}
