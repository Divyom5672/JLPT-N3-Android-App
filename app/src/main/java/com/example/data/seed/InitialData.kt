package com.example.data.seed

import com.example.data.model.Category
import com.example.data.model.FlashcardEntity

object InitialData {

    // Kanji Categories
    val KANJI_CATEGORIES = listOf(
        Category("verbs", "動詞", "Verbs", "Actions & motion", "動"),
        Category("adj", "形容詞", "Adjectives", "i-Adjectives & な-Adjectives", "形"),
        Category("nouns", "名詞", "Nouns / Everyday Objects", "People, places & things", "名"),
        Category("time", "時間", "Time, Sequence & Frequency", "When & how often", "時"),
        Category("people", "人と社会", "People, Relationships & Society", "Who & how we relate", "人"),
        Category("food", "食と生活", "Food & Daily Life", "The everyday routine", "食"),
        Category("work", "学びと仕事", "Study, Work & Communication", "School, career & talk", "学"),
        Category("abstract", "抽象概念", "Abstract Concepts", "Ideas & stances", "念"),
        Category("custom", "新規追加", "Custom Words", "Words created by you", "新")
    )

    // Vocabulary Categories (Distinct from Kanji)
    val VOCAB_CATEGORIES = listOf(
        Category("v_greetings", "挨拶・礼儀", "Greetings & Manners", "Hello, thanks & polite expressions", "挨"),
        Category("v_daily", "日常表現", "Daily Spoken Phrases", "Common conversational idioms", "常"),
        Category("v_travel", "旅行・交通", "Travel & Transit", "Navigating trains, places & asking direction", "旅"),
        Category("v_business", "仕事・敬語", "Business & Formal Talk", "Workplace terms, keigo & office phrases", "企"),
        Category("v_custom", "新規語彙", "Custom Vocabulary", "Vocabulary sets added by you", "新")
    )

    // Legacy default alias
    val CATEGORIES = KANJI_CATEGORIES

    val INITIAL_FLASHCARDS = listOf(
        // Verbs
        FlashcardEntity(categoryId = "verbs", kanji = "押す", hiragana = "おす", meaning = "to push"),
        FlashcardEntity(categoryId = "verbs", kanji = "曲がる", hiragana = "まがる", meaning = "to bend; to turn"),
        FlashcardEntity(categoryId = "verbs", kanji = "置く", hiragana = "おく", meaning = "to put"),
        FlashcardEntity(categoryId = "verbs", kanji = "返す", hiragana = "かえす", meaning = "to give back; to return"),
        FlashcardEntity(categoryId = "verbs", kanji = "拾う", hiragana = "ひろう", meaning = "to pick up"),
        FlashcardEntity(categoryId = "verbs", kanji = "冷やす", hiragana = "ひやす", meaning = "to chill; cool"),
        FlashcardEntity(categoryId = "verbs", kanji = "疲れる", hiragana = "つかれる", meaning = "to get tired"),
        FlashcardEntity(categoryId = "verbs", kanji = "捨てる", hiragana = "すてる", meaning = "to throw away"),
        FlashcardEntity(categoryId = "verbs", kanji = "覚える", hiragana = "おぼえる", meaning = "to remember; learn"),
        FlashcardEntity(categoryId = "verbs", kanji = "洗う", hiragana = "あらう", meaning = "to wash"),
        FlashcardEntity(categoryId = "verbs", kanji = "働く", hiragana = "はたらく", meaning = "to work"),
        FlashcardEntity(categoryId = "verbs", kanji = "合う", hiragana = "あう", meaning = "to fit; match"),
        FlashcardEntity(categoryId = "verbs", kanji = "支える", hiragana = "ささえる", meaning = "to support"),

        // Adjectives
        FlashcardEntity(categoryId = "adj", kanji = "深い", hiragana = "ふかい", meaning = "deep"),
        FlashcardEntity(categoryId = "adj", kanji = "厚い", hiragana = "あつい", meaning = "thick"),
        FlashcardEntity(categoryId = "adj", kanji = "暑い", hiragana = "あつい", meaning = "hot (weather)"),
        FlashcardEntity(categoryId = "adj", kanji = "暗い", hiragana = "くらい", meaning = "dark"),
        FlashcardEntity(categoryId = "adj", kanji = "涼しい", hiragana = "すずしい", meaning = "cool (weather)"),
        FlashcardEntity(categoryId = "adj", kanji = "珍しい", hiragana = "めずらしい", meaning = "rare; unusual"),
        FlashcardEntity(categoryId = "adj", kanji = "恐ろしい", hiragana = "おそろしい", meaning = "dreadful; terrifying"),
        FlashcardEntity(categoryId = "adj", kanji = "険しい", hiragana = "けわしい", meaning = "steep; harsh"),
        FlashcardEntity(categoryId = "adj", kanji = "激しい", hiragana = "はげしい", meaning = "intense; violent"),
        FlashcardEntity(categoryId = "adj", kanji = "眩しい", hiragana = "まぶしい", meaning = "dazzling"),
        FlashcardEntity(categoryId = "adj", kanji = "甚だしい", hiragana = "はなはだしい", meaning = "extreme"),
        FlashcardEntity(categoryId = "adj", kanji = "忌々しい", hiragana = "いまいましい", meaning = "irritating"),
        FlashcardEntity(categoryId = "adj", kanji = "望ましい", hiragana = "のぞましい", meaning = "desirable"),
        FlashcardEntity(categoryId = "adj", kanji = "悔しい", hiragana = "くやしい", meaning = "frustrating; regrettable"),
        FlashcardEntity(categoryId = "adj", kanji = "空しい", hiragana = "むなしい", meaning = "empty; futile"),
        FlashcardEntity(categoryId = "adj", kanji = "淡い", hiragana = "あわい", meaning = "faint; pale"),
        FlashcardEntity(categoryId = "adj", kanji = "怪しい", hiragana = "あやしい", meaning = "suspicious"),
        FlashcardEntity(categoryId = "adj", kanji = "惜しい", hiragana = "おしい", meaning = "regrettable"),
        FlashcardEntity(categoryId = "adj", kanji = "著しい", hiragana = "いちじるしい", meaning = "remarkable"),
        FlashcardEntity(categoryId = "adj", kanji = "煩わしい", hiragana = "わずらわしい", meaning = "troublesome"),
        FlashcardEntity(categoryId = "adj", kanji = "汚らわしい", hiragana = "けがらわしい", meaning = "filthy; disgusting"),
        FlashcardEntity(categoryId = "adj", kanji = "荒々しい", hiragana = "あらあらしい", meaning = "rough; violent"),
        FlashcardEntity(categoryId = "adj", kanji = "微笑ましい", hiragana = "ほほえましい", meaning = "heartwarming"),
        FlashcardEntity(categoryId = "adj", kanji = "悩ましい", hiragana = "なやましい", meaning = "troubling; seductive"),
        FlashcardEntity(categoryId = "adj", kanji = "由々しい", hiragana = "ゆゆしい", meaning = "grave; serious"),

        // na-Adjectives
        FlashcardEntity(categoryId = "adj", kanji = "親切", hiragana = "しんせつ", meaning = "kind; helpful"),
        FlashcardEntity(categoryId = "adj", kanji = "丁寧", hiragana = "ていねい", meaning = "polite; careful"),
        FlashcardEntity(categoryId = "adj", kanji = "盛ん", hiragana = "さかん", meaning = "popular; prosperous"),
        FlashcardEntity(categoryId = "adj", kanji = "静か", hiragana = "しずか", meaning = "quiet"),
        FlashcardEntity(categoryId = "adj", kanji = "賑やか", hiragana = "にぎやか", meaning = "lively; bustling"),
        FlashcardEntity(categoryId = "adj", kanji = "快適", hiragana = "かいてき", meaning = "comfortable"),
        FlashcardEntity(categoryId = "adj", kanji = "豪華", hiragana = "ごうか", meaning = "gorgeous; luxurious"),
        FlashcardEntity(categoryId = "adj", kanji = "粗末", hiragana = "そまつ", meaning = "crude; plain"),
        FlashcardEntity(categoryId = "adj", kanji = "哀れ", hiragana = "あわれ", meaning = "pitiful; pathetic"),
        FlashcardEntity(categoryId = "adj", kanji = "純粋", hiragana = "じゅんすい", meaning = "pure; genuine"),

        // Nouns
        FlashcardEntity(categoryId = "nouns", kanji = "階段", hiragana = "かいだん", meaning = "stairs"),
        FlashcardEntity(categoryId = "nouns", kanji = "神", hiragana = "かみ", meaning = "god; deity"),
        FlashcardEntity(categoryId = "nouns", kanji = "葉", hiragana = "は", meaning = "leaf"),
        FlashcardEntity(categoryId = "nouns", kanji = "星", hiragana = "ほし", meaning = "star"),
        FlashcardEntity(categoryId = "nouns", kanji = "声", hiragana = "こえ", meaning = "voice"),
        FlashcardEntity(categoryId = "nouns", kanji = "景色", hiragana = "けしき", meaning = "scenery"),
        FlashcardEntity(categoryId = "nouns", kanji = "道具", hiragana = "どうぐ", meaning = "tool; instrument"),
        FlashcardEntity(categoryId = "nouns", kanji = "感情", hiragana = "かんじょう", meaning = "emotion; feeling"),
        FlashcardEntity(categoryId = "nouns", kanji = "目的", hiragana = "もくてき", meaning = "purpose; objective"),
        FlashcardEntity(categoryId = "nouns", kanji = "結果", hiragana = "けっか", meaning = "result; outcome"),

        // Time
        FlashcardEntity(categoryId = "time", kanji = "週末", hiragana = "しゅうまつ", meaning = "weekend"),
        FlashcardEntity(categoryId = "time", kanji = "次回", hiragana = "じかい", meaning = "next time; next occasion"),
        FlashcardEntity(categoryId = "time", kanji = "慌ただしい", hiragana = "あわただしい", meaning = "hurried; busy"),
        FlashcardEntity(categoryId = "time", kanji = "忙しない", hiragana = "せわしない", meaning = "busy; restless"),
        FlashcardEntity(categoryId = "time", kanji = "果てしない", hiragana = "はてしない", meaning = "endless"),
        FlashcardEntity(categoryId = "time", kanji = "儚い", hiragana = "はかない", meaning = "fleeting"),
        FlashcardEntity(categoryId = "time", kanji = "名残惜しい", hiragana = "なごりおしい", meaning = "reluctant to part"),
        FlashcardEntity(categoryId = "time", kanji = "永久", hiragana = "えいきゅう", meaning = "eternity; permanence"),
        FlashcardEntity(categoryId = "time", kanji = "瞬間", hiragana = "しゅんかん", meaning = "moment; instant"),

        // People & Society
        FlashcardEntity(categoryId = "people", kanji = "相手", hiragana = "あいて", meaning = "partner; companion; opponent"),
        FlashcardEntity(categoryId = "people", kanji = "熱心", hiragana = "ねっしん", meaning = "enthusiastic; devoted"),
        FlashcardEntity(categoryId = "people", kanji = "逞しい", hiragana = "たくましい", meaning = "sturdy; robust"),
        FlashcardEntity(categoryId = "people", kanji = "相応しい", hiragana = "ふさわしい", meaning = "suitable"),
        FlashcardEntity(categoryId = "people", kanji = "喜ばしい", hiragana = "よろこばしい", meaning = "joyful"),
        FlashcardEntity(categoryId = "people", kanji = "憎らしい", hiragana = "にくらしい", meaning = "hateful"),
        FlashcardEntity(categoryId = "people", kanji = "頼もしい", hiragana = "たのもしい", meaning = "reliable"),
        FlashcardEntity(categoryId = "people", kanji = "好ましい", hiragana = "このましい", meaning = "favorable"),
        FlashcardEntity(categoryId = "people", kanji = "図々しい", hiragana = "ずうずうしい", meaning = "shameless; brazen"),
        FlashcardEntity(categoryId = "people", kanji = "若々しい", hiragana = "わかわかしい", meaning = "youthful"),
        FlashcardEntity(categoryId = "people", kanji = "卑しい", hiragana = "いやしい", meaning = "vulgar; lowly"),
        FlashcardEntity(categoryId = "people", kanji = "賢い", hiragana = "かしこい", meaning = "wise; clever"),
        FlashcardEntity(categoryId = "people", kanji = "潔い", hiragana = "いさぎよい", meaning = "resolute"),
        FlashcardEntity(categoryId = "people", kanji = "幼い", hiragana = "おさない", meaning = "childish; young"),
        FlashcardEntity(categoryId = "people", kanji = "馴れ馴れしい", hiragana = "なれなれしい", meaning = "overly familiar"),
        FlashcardEntity(categoryId = "people", kanji = "いやらしい", hiragana = "いやらしい", meaning = "disgusting; indecent"),

        // Food & Daily Life
        FlashcardEntity(categoryId = "food", kanji = "洗濯", hiragana = "せんたく", meaning = "laundry"),
        FlashcardEntity(categoryId = "food", kanji = "食費", hiragana = "しょくひ", meaning = "food expenses"),
        FlashcardEntity(categoryId = "food", kanji = "好き", hiragana = "すき", meaning = "liked; fond of"),
        FlashcardEntity(categoryId = "food", kanji = "苦しい", hiragana = "くるしい", meaning = "painful"),
        FlashcardEntity(categoryId = "food", kanji = "貧しい", hiragana = "まずしい", meaning = "poor"),
        FlashcardEntity(categoryId = "food", kanji = "眠い", hiragana = "ねむい", meaning = "sleepy"),
        FlashcardEntity(categoryId = "food", kanji = "料理", hiragana = "りょうり", meaning = "cooking; cuisine"),
        FlashcardEntity(categoryId = "food", kanji = "掃除", hiragana = "そうじ", meaning = "cleaning"),

        // Study & Work
        FlashcardEntity(categoryId = "work", kanji = "予約", hiragana = "よやく", meaning = "reservation; appointment"),
        FlashcardEntity(categoryId = "work", kanji = "辞書", hiragana = "じしょ", meaning = "dictionary"),
        FlashcardEntity(categoryId = "work", kanji = "面接", hiragana = "めんせつ", meaning = "interview"),
        FlashcardEntity(categoryId = "work", kanji = "倍率", hiragana = "ばいりつ", meaning = "magnification; ratio"),
        FlashcardEntity(categoryId = "work", kanji = "取材", hiragana = "しゅざい", meaning = "interview; coverage; reporting"),
        FlashcardEntity(categoryId = "work", kanji = "退学", hiragana = "たいがく", meaning = "leaving school; withdrawal from school"),
        FlashcardEntity(categoryId = "work", kanji = "化学", hiragana = "かがく", meaning = "chemistry"),
        FlashcardEntity(categoryId = "work", kanji = "役に立つ", hiragana = "やくにたつ", meaning = "to be useful"),
        FlashcardEntity(categoryId = "work", kanji = "文化", hiragana = "ぶんか", meaning = "culture"),
        FlashcardEntity(categoryId = "work", kanji = "説明", hiragana = "せつめい", meaning = "explanation"),
        FlashcardEntity(categoryId = "work", kanji = "求人", hiragana = "きゅうじん", meaning = "recruiting; job opening"),
        FlashcardEntity(categoryId = "work", kanji = "市役所", hiragana = "しやくしょ", meaning = "city hall"),
        FlashcardEntity(categoryId = "work", kanji = "詳しい", hiragana = "くわしい", meaning = "detailed"),
        FlashcardEntity(categoryId = "work", kanji = "疑わしい", hiragana = "うたがわしい", meaning = "doubtful"),

        // Abstract Concepts
        FlashcardEntity(categoryId = "abstract", kanji = "挑戦", hiragana = "ちょうせん", meaning = "challenge"),
        FlashcardEntity(categoryId = "abstract", kanji = "活動", hiragana = "かつどう", meaning = "activity"),
        FlashcardEntity(categoryId = "abstract", kanji = "借金", hiragana = "しゃっきん", meaning = "debt"),
        FlashcardEntity(categoryId = "abstract", kanji = "種類", hiragana = "しゅるい", meaning = "kind; type; category"),
        FlashcardEntity(categoryId = "abstract", kanji = "賛成", hiragana = "さんせい", meaning = "agreement; approval; being in favor"),
        FlashcardEntity(categoryId = "abstract", kanji = "反対", hiragana = "はんたい", meaning = "opposition; being against"),
        FlashcardEntity(categoryId = "abstract", kanji = "建設", hiragana = "けんせつ", meaning = "construction"),
        FlashcardEntity(categoryId = "abstract", kanji = "都合", hiragana = "つごう", meaning = "circumstances; convenience"),
        FlashcardEntity(categoryId = "abstract", kanji = "区別", hiragana = "くべつ", meaning = "distinction; classification"),
        FlashcardEntity(categoryId = "abstract", kanji = "失敗", hiragana = "しっぱい", meaning = "failure"),
        FlashcardEntity(categoryId = "abstract", kanji = "原因", hiragana = "げんいん", meaning = "cause; reason"),
        FlashcardEntity(categoryId = "abstract", kanji = "理由", hiragana = "りゆう", meaning = "reason"),
        FlashcardEntity(categoryId = "abstract", kanji = "国際", hiragana = "こくさい", meaning = "international"),
        FlashcardEntity(categoryId = "abstract", kanji = "祝福", hiragana = "しゅくふく", meaning = "blessing; congratulations")
    ).distinctBy { Pair(it.categoryId, it.kanji) }

    // Initial Vocabulary Cards (Sample Phrases)
    val INITIAL_VOCAB_FLASHCARDS = listOf(
        // Greetings & Manners
        FlashcardEntity(categoryId = "v_greetings", kanji = "こんにちは", hiragana = "こんにちは", meaning = "Hello / Good afternoon"),
        FlashcardEntity(categoryId = "v_greetings", kanji = "ありがとうございます", hiragana = "ありがとうございます", meaning = "Thank you very much"),
        FlashcardEntity(categoryId = "v_greetings", kanji = "いただきます", hiragana = "いただきます", meaning = "Thank you for the meal (before eating)"),
        FlashcardEntity(categoryId = "v_greetings", kanji = "ごちそうさまでした", hiragana = "ごちそうさまでした", meaning = "Thank you for the meal (after eating)"),
        FlashcardEntity(categoryId = "v_greetings", kanji = "すみません", hiragana = "すみません", meaning = "Excuse me; I'm sorry"),
        FlashcardEntity(categoryId = "v_greetings", kanji = "お疲れ様です", hiragana = "おつかれさまです", meaning = "Thank you for your hard work"),

        // Daily Expressions
        FlashcardEntity(categoryId = "v_daily", kanji = "お世話になっております", hiragana = "おせわになっております", meaning = "Thank you for your continued support"),
        FlashcardEntity(categoryId = "v_daily", kanji = "気をつけてください", hiragana = "きをつけてください", meaning = "Please take care; stay safe"),
        FlashcardEntity(categoryId = "v_daily", kanji = "なるほど", hiragana = "なるほど", meaning = "I see; that makes sense"),

        // Travel
        FlashcardEntity(categoryId = "v_travel", kanji = "切符売り場", hiragana = "きっぷうりば", meaning = "Ticket office / Vending area"),
        FlashcardEntity(categoryId = "v_travel", kanji = "乗り換え", hiragana = "のりかえ", meaning = "Transfer (train / bus)"),

        // Business
        FlashcardEntity(categoryId = "v_business", kanji = "少々お待ちください", hiragana = "しょうしょうおまちください", meaning = "Please wait a moment (polite)")
    ).distinctBy { Pair(it.categoryId, it.kanji) }
}
