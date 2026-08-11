package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: String,
    val kanji: String,
    val hiragana: String,
    val meaning: String,
    val exampleJp: String = "",
    val exampleEn: String = "",
    val isBookmarked: Boolean = false,
    val status: String = "NEW", // "NEW", "REVIEW", "MASTERED"
    val timesReviewed: Int = 0,
    val lastReviewedTime: Long = 0L,
    val isCustom: Boolean = false
)

data class Category(
    val id: String,
    val jpName: String,
    val enName: String,
    val description: String,
    val glyph: String
)

enum class FlashcardFilter {
    ALL,
    NEW,
    REVIEW,
    MASTERED,
    BOOKMARKED
}
