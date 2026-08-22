package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FlashcardEntity
import com.example.data.model.WeekInfo
import com.example.data.seed.InitialData

@Composable
fun WeekSelectScreen(
    sectionType: String, // "kanji" or "vocab"
    allCards: List<FlashcardEntity>,
    onSelectWeek: (Int) -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weeks = InitialData.getWeeks(sectionType)
    val isKanji = sectionType == "kanji"
    val sectionTitleJp = if (isKanji) "漢字コース" else "単語コース"
    val sectionTitleEn = if (isKanji) "Kanji Course" else "Vocabulary Course"
    val prefix = if (isKanji) "w" else "v_w"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF161922),
                        Color(0xFF1E2330),
                        Color(0xFF131722)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Navigation & Breadcrumbs Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBackToHome,
                        modifier = Modifier.testTag("week_back_to_home")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = Color(0xFFE2B670)
                        )
                    }

                    Surface(
                        color = Color(0xFFBA3C2A),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 6.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = if (isKanji) "漢" else "語",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = sectionTitleJp,
                        color = Color(0xFFFAF8F5),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                // Breadcrumbs on top right
                Text(
                    text = "Home / $sectionTitleEn",
                    color = Color(0xFF808CA2),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Main Screen Header Title
            Text(
                text = "$sectionTitleEn · Weeks",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFFAF8F5),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Select a week to access its 7 daily study modules (Days 1–7)",
                fontSize = 13.sp,
                color = Color(0xFF939BB0),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Grid of Week Cards
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(weeks, key = { it.weekNumber }) { week ->
                    val weekCards = allCards.filter { 
                        if (isKanji) {
                            it.categoryId.startsWith("w${week.weekNumber}_")
                        } else {
                            it.categoryId.startsWith("v_w${week.weekNumber}_")
                        }
                    }
                    val cardCount = weekCards.size
                    val masteredCount = weekCards.count { it.status == "MASTERED" }

                    WeekGridCard(
                        week = week,
                        cardCount = cardCount,
                        masteredCount = masteredCount,
                        onClick = { onSelectWeek(week.weekNumber) }
                    )
                }
            }
        }
    }
}

@Composable
private fun WeekGridCard(
    week: WeekInfo,
    cardCount: Int,
    masteredCount: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2538)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.15f)
            .clickable { onClick() }
            .testTag("week_card_${week.weekNumber}")
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            // Large Faded Watermark Glyph on Top Right
            Text(
                text = week.glyph,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF334155).copy(alpha = 0.40f),
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Japanese Week Title
                    Text(
                        text = week.jpName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFFE2B670) // Warm Gold
                    )

                    // English Week Subtitle
                    Text(
                        text = "${week.enName} · Days 1–7",
                        fontSize = 12.sp,
                        color = Color(0xFF94A3B8),
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Count Pill Badge
                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                    ) {
                        Text(
                            text = "$cardCount cards",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF94A3B8),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    if (masteredCount > 0) {
                        Text(
                            text = "✓ $masteredCount",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ADE80)
                        )
                    }
                }
            }
        }
    }
}
