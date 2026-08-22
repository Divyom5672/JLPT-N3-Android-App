package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FlashcardEntity
import com.example.ui.components.FallingLeavesCanvas

@Composable
fun HomeScreen(
    allCards: List<FlashcardEntity> = emptyList(),
    onSelectSection: (String) -> Unit, // "kanji" or "vocab"
    modifier: Modifier = Modifier
) {
    val kanjiCards = allCards.filter { !it.categoryId.startsWith("v_") }
    val vocabCards = allCards.filter { it.categoryId.startsWith("v_") }
    
    val kanjiCount = if (kanjiCards.isNotEmpty()) kanjiCards.size else 846
    val vocabCount = if (vocabCards.isNotEmpty()) vocabCards.size else 1117
    
    val kanjiMastered = kanjiCards.count { it.status == "MASTERED" }
    val vocabMastered = vocabCards.count { it.status == "MASTERED" }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2E1318), // Dark Burgundy Top
                        Color(0xFF70281C), // Warm Crimson/Orange Sunset
                        Color(0xFFC35626), // Glowing Horizon
                        Color(0xFF283144), // Deep Mountain Blue
                        Color(0xFF141A28)  // Dark Indigo Navy Bottom
                    )
                )
            )
    ) {
        // Landscape Graphics Canvas (Mountain Peaks & Glowing Sun & Torii Gate)
        HomeLandscapeCanvas(modifier = Modifier.fillMaxSize())

        // Animated Falling Leaves Particle Layer
        FallingLeavesCanvas(
            leafCount = 22,
            modifier = Modifier.fillMaxSize()
        )

        // Main Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle Header
            Text(
                text = "JLPT N3 STUDY COMPANION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color(0xFFE2B670),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Main Title "日本語の道" / "漢字の道"
            Text(
                text = "日本語の道",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFFAF8F5),
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Badge
            Surface(
                color = Color(0xFF221815).copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2B670).copy(alpha = 0.6f))
            ) {
                Text(
                    text = "漢字 Kanji & 単語 Vocabulary Complete System",
                    fontSize = 11.sp,
                    color = Color(0xFFE2B670),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Description Paragraph
            Text(
                text = "Master JLPT N3 with structured weekly modules (Weeks 1–6 / 1–8) divided into daily sets (Days 1–7). Learn through comprehensive word lists and interactive flashcards.",
                fontSize = 13.sp,
                color = Color(0xFFDDD6CE),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.widthIn(max = 420.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Section Cards Grid (Kanji & Vocabulary)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 680.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // 1. Kanji Card
                SectionHomeCard(
                    jpTitle = "漢字",
                    enTitle = "Kanji Course",
                    planBadge = "6 Weeks · 42 Days · $kanjiCount Cards",
                    description = "Comprehensive characters with on/kun readings and English meanings.",
                    masteredCount = kanjiMastered,
                    watermarkGlyph = "漢",
                    buttonText = "Study Kanji",
                    onClick = { onSelectSection("kanji") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_card_kanji")
                )

                // 2. Vocabulary Card
                SectionHomeCard(
                    jpTitle = "単語",
                    enTitle = "Vocabulary Course",
                    planBadge = "8 Weeks · 56 Days · $vocabCount Words",
                    description = "Core N3 vocabulary words with furigana readings and definitions.",
                    masteredCount = vocabMastered,
                    watermarkGlyph = "語",
                    buttonText = "Study Vocabulary",
                    onClick = { onSelectSection("vocab") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_card_vocab")
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Study Overview Bar
            Surface(
                color = Color(0xFF1E2433).copy(alpha = 0.85f),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B4861)),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 680.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${kanjiCount + vocabCount}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE2B670)
                        )
                        Text(
                            text = "Total Cards",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${kanjiMastered + vocabMastered}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ADE80)
                        )
                        Text(
                            text = "Mastered",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "14 Weeks",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF97316)
                        )
                        Text(
                            text = "98 Daily Sets",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Footer Quote
            Text(
                text = "「継続は力なり」 — Continuity is strength.",
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFE2B670).copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionHomeCard(
    jpTitle: String,
    enTitle: String,
    planBadge: String,
    description: String,
    masteredCount: Int,
    watermarkGlyph: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFE8DB).copy(alpha = 0.95f) // Warm parchment
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Faded Japanese Glyph Watermark in bottom/right
            Text(
                text = watermarkGlyph,
                fontSize = 90.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF1E212B).copy(alpha = 0.10f),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 0.dp, end = 4.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        // Japanese Header Title
                        Text(
                            text = jpTitle,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF1E212B)
                        )

                        // English Subtitle
                        Text(
                            text = enTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2B2E3A)
                        )
                    }

                    // Plan Badge
                    Surface(
                        color = Color(0xFF1E212B).copy(alpha = 0.08f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = planBadge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF70281C),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color(0xFF4A4E5A),
                    lineHeight = 18.sp,
                    modifier = Modifier.widthIn(max = 420.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (masteredCount > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF16A34A),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = "$masteredCount mastered",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF16A34A)
                            )
                        }
                    } else {
                        Text(
                            text = "Days 1 – 7 per week",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF71717A)
                        )
                    }

                    // Terracotta Red "Begin ->" Button
                    Button(
                        onClick = onClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBA3C2A),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = buttonText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeLandscapeCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // 1. Glowing Sunset Orb behind mountain
        val sunCenter = Offset(width * 0.5f, height * 0.36f)
        val sunRadius = width * 0.28f

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFDD99).copy(alpha = 0.75f),
                    Color(0xFFE27B38).copy(alpha = 0.45f),
                    Color(0x00E27B38)
                ),
                center = sunCenter,
                radius = sunRadius * 1.5f
            ),
            radius = sunRadius * 1.5f,
            center = sunCenter
        )

        drawCircle(
            color = Color(0xFFFFAE66).copy(alpha = 0.6f),
            radius = sunRadius * 0.7f,
            center = sunCenter
        )

        // 2. Distant Soft Mountain Ridges (Layer 1)
        val pathDistant = Path().apply {
            moveTo(0f, height * 0.46f)
            lineTo(width * 0.22f, height * 0.38f)
            lineTo(width * 0.50f, height * 0.43f)
            lineTo(width * 0.78f, height * 0.35f)
            lineTo(width, height * 0.45f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(
            path = pathDistant,
            color = Color(0xFF6B2620).copy(alpha = 0.45f)
        )

        // 3. Mid-range Mountain Silhouette (Mt Fuji style peak)
        val pathMid = Path().apply {
            moveTo(0f, height * 0.54f)
            lineTo(width * 0.28f, height * 0.48f)
            lineTo(width * 0.50f, height * 0.40f) // Center Fuji Peak
            lineTo(width * 0.72f, height * 0.49f)
            lineTo(width, height * 0.56f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(
            path = pathMid,
            color = Color(0xFF381F28).copy(alpha = 0.65f)
        )

        // 4. Foreground Mountain Ridge & Dark Indigo Base
        val pathFore = Path().apply {
            moveTo(0f, height * 0.62f)
            cubicTo(
                width * 0.3f, height * 0.58f,
                width * 0.6f, height * 0.64f,
                width, height * 0.60f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(
            path = pathFore,
            color = Color(0xFF181E2E).copy(alpha = 0.85f)
        )

        // 5. Stylized Torii Gate Silhouette on right slope
        val toriiX = width * 0.82f
        val toriiY = height * 0.58f
        val toriiScale = 0.7f

        val toriiColor = Color(0xFF8B2516).copy(alpha = 0.55f)

        // Main lintel (curved top beam)
        drawRoundRect(
            color = toriiColor,
            topLeft = Offset(toriiX - 24.dp.toPx() * toriiScale, toriiY - 22.dp.toPx() * toriiScale),
            size = androidx.compose.ui.geometry.Size(48.dp.toPx() * toriiScale, 4.dp.toPx() * toriiScale),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
        )
        // Sub-beam
        drawRect(
            color = toriiColor,
            topLeft = Offset(toriiX - 20.dp.toPx() * toriiScale, toriiY - 15.dp.toPx() * toriiScale),
            size = androidx.compose.ui.geometry.Size(40.dp.toPx() * toriiScale, 3.dp.toPx() * toriiScale)
        )
        // Left pillar
        drawRect(
            color = toriiColor,
            topLeft = Offset(toriiX - 12.dp.toPx() * toriiScale, toriiY - 15.dp.toPx() * toriiScale),
            size = androidx.compose.ui.geometry.Size(4.dp.toPx() * toriiScale, 28.dp.toPx() * toriiScale)
        )
        // Right pillar
        drawRect(
            color = toriiColor,
            topLeft = Offset(toriiX + 8.dp.toPx() * toriiScale, toriiY - 15.dp.toPx() * toriiScale),
            size = androidx.compose.ui.geometry.Size(4.dp.toPx() * toriiScale, 28.dp.toPx() * toriiScale)
        )
    }
}
