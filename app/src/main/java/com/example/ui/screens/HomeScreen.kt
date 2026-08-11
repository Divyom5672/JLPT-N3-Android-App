package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.draw.clip
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
import com.example.ui.components.FallingLeavesCanvas

@Composable
fun HomeScreen(
    onNavigateToKanji: () -> Unit,
    onNavigateToVocab: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            Spacer(modifier = Modifier.height(20.dp))

            // Subtitle Header
            Text(
                text = "LEARN AT YOUR OWN PACE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color(0xFFE2B670),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Main Title "漢字の道"
            Text(
                text = "漢字の道",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFFAF8F5),
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // JLPT N3 level Badge
            Surface(
                color = Color(0xFF221815).copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2B670).copy(alpha = 0.6f))
            ) {
                Text(
                    text = "JLPT N3 level",
                    fontSize = 11.sp,
                    color = Color(0xFFE2B670),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description Paragraph
            Text(
                text = "Kanji no Michi — \"the way of kanji.\" Built for JLPT N3 study: work through characters and words in small, focused sets, one card at a time.",
                fontSize = 13.sp,
                color = Color(0xFFDDD6CE),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.widthIn(max = 380.dp)
            )

            Spacer(modifier = Modifier.height(72.dp))

            // Two Title Cards (Kanji & Vocabulary)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 700.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Kanji Card
                    HomeTitleCard(
                        jpTitle = "漢字",
                        enTitle = "Kanji",
                        watermarkGlyph = "漢",
                        buttonText = "Begin",
                        onClick = onNavigateToKanji,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_card_kanji")
                    )

                    // Vocabulary Card
                    HomeTitleCard(
                        jpTitle = "単語",
                        enTitle = "Vocabulary",
                        watermarkGlyph = "語",
                        buttonText = "Begin",
                        onClick = onNavigateToVocab,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_card_vocab")
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Footer Quote
            Text(
                text = "静かに、一歩ずつ — quietly, one step at a time",
                fontSize = 12.sp,
                color = Color(0xFF949EB2),
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HomeTitleCard(
    jpTitle: String,
    enTitle: String,
    watermarkGlyph: String,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF7EE).copy(alpha = 0.45f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Faded Japanese Glyph Watermark in bottom/right
            Text(
                text = watermarkGlyph,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF1E212B).copy(alpha = 0.12f),
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Japanese Header Title
                    Text(
                        text = jpTitle,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFF1E212B)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // English Subtitle
                    Text(
                        text = enTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B2E3A)
                    )
                }

                // Terracotta Red "Begin ->" Button
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBA3C2A),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(36.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
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

@Composable
private fun HomeLandscapeCanvas(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Glowing Sun in background behind mountain
        drawCircle(
            color = Color(0xFFFFEAA7).copy(alpha = 0.85f),
            radius = w * 0.28f,
            center = Offset(w * 0.5f, h * 0.35f)
        )

        // Soft Radial Aura around sun
        drawCircle(
            color = Color(0xFFFFB300).copy(alpha = 0.25f),
            radius = w * 0.42f,
            center = Offset(w * 0.5f, h * 0.35f)
        )

        // Left Mountain Silhouette
        val leftMountain = Path().apply {
            moveTo(0f, h * 0.90f)
            lineTo(w * 0.32f, h * 0.36f)
            lineTo(w * 0.62f, h * 0.90f)
            close()
        }
        drawPath(leftMountain, color = Color(0xFF283248))

        // Right Mountain Silhouette
        val rightMountain = Path().apply {
            moveTo(w * 0.38f, h * 0.90f)
            lineTo(w * 0.68f, h * 0.36f)
            lineTo(w, h * 0.90f)
            close()
        }
        drawPath(rightMountain, color = Color(0xFF202A3D))

        // Center Torii Gate Pillars
        val toriiColor = Color(0xFF7A2017)
        val pLeft = w * 0.45f
        val pRight = w * 0.55f

        // Gate Posts
        drawRect(
            color = toriiColor,
            topLeft = Offset(pLeft, h * 0.58f),
            size = androidx.compose.ui.geometry.Size(12.dp.toPx(), h * 0.32f)
        )
        drawRect(
            color = toriiColor,
            topLeft = Offset(pRight, h * 0.58f),
            size = androidx.compose.ui.geometry.Size(12.dp.toPx(), h * 0.32f)
        )

        // Gate Top Crossbeam
        drawRect(
            color = toriiColor,
            topLeft = Offset(pLeft - 16.dp.toPx(), h * 0.60f),
            size = androidx.compose.ui.geometry.Size(pRight - pLeft + 44.dp.toPx(), 10.dp.toPx())
        )
    }
}
