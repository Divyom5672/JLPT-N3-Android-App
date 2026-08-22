package com.example.data.seed

import com.example.data.model.Category
import com.example.data.model.FlashcardEntity
import com.example.data.model.WeekInfo

object InitialData {

    val KANJI_WEEKS = listOf(
        WeekInfo(1, "第1週", "Week 1", "Days 1 – 7", "一"),
        WeekInfo(2, "第2週", "Week 2", "Days 1 – 7", "二"),
        WeekInfo(3, "第3週", "Week 3", "Days 1 – 7", "三"),
        WeekInfo(4, "第4週", "Week 4", "Days 1 – 7", "四"),
        WeekInfo(5, "第5週", "Week 5", "Days 1 – 7", "五"),
        WeekInfo(6, "第6週", "Week 6", "Days 1 – 7", "六")
    )

    val VOCAB_WEEKS = listOf(
        WeekInfo(1, "第1週", "Week 1", "Days 1 – 7", "一"),
        WeekInfo(2, "第2週", "Week 2", "Days 1 – 7", "二"),
        WeekInfo(3, "第3週", "Week 3", "Days 1 – 7", "三"),
        WeekInfo(4, "第4週", "Week 4", "Days 1 – 7", "四"),
        WeekInfo(5, "第5週", "Week 5", "Days 1 – 7", "五"),
        WeekInfo(6, "第6週", "Week 6", "Days 1 – 7", "六"),
        WeekInfo(7, "第7週", "Week 7", "Days 1 – 7", "七"),
        WeekInfo(8, "第8週", "Week 8", "Days 1 – 7", "八")
    )

    val WEEKS = KANJI_WEEKS

    private val DAY_GLYPHS = listOf("一", "二", "三", "四", "五", "六", "七")

    val KANJI_CATEGORIES: List<Category> = (1..6).flatMap { weekNum ->
        (1..7).map { dayNum ->
            Category(
                id = "w${weekNum}_d${dayNum}",
                sectionType = "kanji",
                weekNumber = weekNum,
                dayNumber = dayNum,
                jpName = "第${dayNum}日",
                enName = "Day $dayNum",
                description = "Kanji · Week $weekNum · Day $dayNum",
                glyph = DAY_GLYPHS[dayNum - 1]
            )
        }
    }

    val VOCAB_CATEGORIES: List<Category> = (1..8).flatMap { weekNum ->
        (1..7).map { dayNum ->
            Category(
                id = "v_w${weekNum}_d${dayNum}",
                sectionType = "vocab",
                weekNumber = weekNum,
                dayNumber = dayNum,
                jpName = "第${dayNum}日",
                enName = "Day $dayNum",
                description = "Vocabulary · Week $weekNum · Day $dayNum",
                glyph = DAY_GLYPHS[dayNum - 1]
            )
        }
    }

    val CATEGORIES: List<Category> = KANJI_CATEGORIES + VOCAB_CATEGORIES

    fun getWeeks(sectionType: String): List<WeekInfo> {
        return if (sectionType == "vocab") VOCAB_WEEKS else KANJI_WEEKS
    }

    fun getDaysForWeek(sectionType: String, weekNum: Int): List<Category> {
        return if (sectionType == "vocab") {
            VOCAB_CATEGORIES.filter { it.weekNumber == weekNum }
        } else {
            KANJI_CATEGORIES.filter { it.weekNumber == weekNum }
        }
    }

    fun getDaysForWeek(weekNum: Int): List<Category> {
        return KANJI_CATEGORIES.filter { it.weekNumber == weekNum }
    }

    fun getCategoryById(id: String?): Category? {
        return CATEGORIES.find { it.id == id }
    }

    val INITIAL_FLASHCARDS: List<FlashcardEntity> = 
        Week1Data.cards +
        Week2Data.cards +
        Week3Data.cards +
        Week4Data.cards +
        Week5Data.cards +
        Week6Data.cards +
        VocabWeek1Data.cards +
        VocabWeek2Data.cards +
        VocabWeek3Data.cards +
        VocabWeek4Data.cards +
        VocabWeek5Data.cards +
        VocabWeek6Data.cards +
        VocabWeek7Data.cards +
        VocabWeek8Data.cards
}
