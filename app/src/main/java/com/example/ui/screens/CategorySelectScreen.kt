package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.FlashcardEntity

@Composable
fun CategorySelectScreen(
    categories: List<Category>,
    allCards: List<FlashcardEntity>,
    sectionType: String = "Kanji", // "Kanji" or "Vocabulary"
    onCategorySelected: (Category) -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)) // Deep Indigo Navy
    ) {
        // Subtle Pattern Background Overlay
        SubtleCircleGridPattern(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Top App Bar / Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconButton(
                        onClick = onBackToHome,
                        modifier = Modifier.testTag("category_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = Color(0xFFE2B670)
                        )
                    }

                    // Logo Icon Square
                    Surface(
                        color = Color(0xFFC0392B),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "道",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "漢字の道",
                        color = Color(0xFFFAF8F5),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                // Breadcrumbs on top right
                Text(
                    text = "漢字の道 / $sectionType",
                    color = Color(0xFF808CA2),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Main Screen Header Title
            Text(
                text = "Choose a category",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFFAF8F5),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (sectionType == "Vocabulary") "Pick a set of vocabulary words to study as a list or with flashcards." else "Pick a set of kanji to study as a list or with flashcards.",
                fontSize = 13.sp,
                color = Color(0xFF939BB0),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Grid of Category Cards
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories, key = { it.id }) { category ->
                    val cardCount = if (category.id == "custom") {
                        allCards.count { it.isCustom || it.categoryId == "custom" }
                    } else {
                        allCards.count { it.categoryId == category.id }
                    }

                    CategoryGridCard(
                        category = category,
                        itemCount = cardCount,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryGridCard(
    category: Category,
    itemCount: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B) // Deep Navy Card Surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .clickable { onClick() }
            .testTag("category_card_${category.id}")
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Large Faded Watermark Glyph on Top Right
            Text(
                text = category.glyph,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF334155).copy(alpha = 0.45f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .alpha(0.5f)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Japanese Category Name
                    Text(
                        text = category.jpName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color(0xFFE2B670) // Warm Gold
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // English Category Name
                    Text(
                        text = category.enName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF1F5F9)
                    )
                }

                // Count Pill Badge at Bottom Left
                Surface(
                    color = Color(0xFF0F172A),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                ) {
                    Text(
                        text = "$itemCount words",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SubtleCircleGridPattern(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val radius = 3.dp.toPx()
        val spacing = 28.dp.toPx()
        val color = Color(0xFF1E293B).copy(alpha = 0.35f)

        var y = 0f
        while (y < size.height) {
            var x = 0f
            while (x < size.width) {
                drawCircle(color = color, radius = radius, center = Offset(x, y))
                x += spacing
            }
            y += spacing
        }
    }
}
