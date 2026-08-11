package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Category
import com.example.data.model.FlashcardEntity
import com.example.ui.components.AddCardBottomSheet
import com.example.ui.components.SwipeableCardDeck
import com.example.ui.viewmodel.FlashcardUiState
import com.example.ui.viewmodel.FlashcardViewModel

@Composable
fun SectionDetailScreen(
    category: Category,
    state: FlashcardUiState,
    viewModel: FlashcardViewModel,
    onBackToCategories: () -> Unit,
    modifier: Modifier = Modifier
) {
    var viewMode by remember { mutableStateOf("List") } // "List" or "Flashcards"
    var showAddDialog by remember { mutableStateOf(false) }

    // Filter cards for this category
    val cards = if (category.id == "custom") {
        state.allCards.filter { it.isCustom || it.categoryId == "custom" }
    } else {
        state.allCards.filter { it.categoryId == category.id }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A)) // Deep Navy Background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackToCategories,
                    modifier = Modifier.testTag("section_detail_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFFE2B670)
                    )
                }

                Text(
                    text = if (category.id.startsWith("v_")) "語彙の道 / ${category.jpName}" else "漢字の道 / ${category.jpName}",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Hero Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large Japanese Category Title
                Text(
                    text = category.jpName,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFFE2B670), // Warm Gold
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(2.dp))

                // English Title
                Text(
                    text = category.enName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFFF8FAFC),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Item count caption
                Text(
                    text = "${cards.size} words in this set",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Control Toggle Bar (List | Flashcards | + Button)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Pill toggle container
                    Surface(
                        color = Color(0xFF1E293B),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                    ) {
                        Row(
                            modifier = Modifier.padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // List Button
                            Button(
                                onClick = { viewMode = "List" },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (viewMode == "List") Color(0xFFC0392B) else Color.Transparent,
                                    contentColor = Color.White
                                ),
                                elevation = null,
                                modifier = Modifier
                                    .height(36.dp)
                                    .testTag("toggle_list_mode")
                            ) {
                                Text(
                                    text = "List",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Flashcards Button
                            Button(
                                onClick = {
                                    viewModel.selectCategory(category.id)
                                    viewModel.randomizeDeck()
                                    viewMode = "Flashcards"
                                },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (viewMode == "Flashcards") Color(0xFFC0392B) else Color.Transparent,
                                    contentColor = Color.White
                                ),
                                elevation = null,
                                modifier = Modifier
                                    .height(36.dp)
                                    .testTag("toggle_flashcards_mode")
                            ) {
                                Text(
                                    text = "Flashcards",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Add Custom Word Button "+"
                    IconButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B))
                            .border(1.5.dp, Color(0xFFE2B670), CircleShape)
                            .testTag("add_custom_word_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Custom Word",
                            tint = Color(0xFFE2B670)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Main Content Body (List or Flashcards)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (viewMode == "List") {
                    // Cream/Paper List Container matching Image 3
                    Surface(
                        color = Color(0xFFFBF8EE), // Warm Off-White / Cream Paper
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (cards.isEmpty()) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize().padding(32.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "No words in this set yet.",
                                        color = Color(0xFF7A7060),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Button(
                                        onClick = { showAddDialog = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0392B))
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Add First Kanji")
                                    }
                                }
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp, vertical = 16.dp)
                            ) {
                                itemsIndexed(cards, key = { _, card -> card.id }) { index, card ->
                                    WordListItemRow(
                                        indexNumber = String.format("%02d", index + 1),
                                        card = card,
                                        onToggleBookmark = { viewModel.toggleBookmark(card) }
                                    )

                                    if (index < cards.size - 1) {
                                        HorizontalDivider(
                                            color = Color(0xFFEBE5D5),
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Flashcards Swipeable Deck View
                    StudyDeckScreen(
                        state = state,
                        viewModel = viewModel,
                        onGoBack = onBackToCategories
                    )
                }
            }
        }

        // Add Custom Card BottomSheet / Dialog
        if (showAddDialog) {
            AddCardBottomSheet(
                categories = if (category.id.startsWith("v_")) com.example.data.seed.InitialData.VOCAB_CATEGORIES else com.example.data.seed.InitialData.KANJI_CATEGORIES,
                initialCategoryId = category.id,
                onDismiss = { showAddDialog = false },
                onAddCard = { kanji, hiragana, meaning, selectedCatId ->
                    viewModel.addCustomCard(kanji, hiragana, meaning, selectedCatId)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
private fun WordListItemRow(
    indexNumber: String,
    card: FlashcardEntity,
    onToggleBookmark: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Column: Kanji character
        Text(
            text = card.kanji,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF1E212B),
            modifier = Modifier.widthIn(min = 70.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Middle Column: Hiragana and Meaning
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = card.hiragana,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC0392B) // Terracotta Red Reading
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = card.meaning,
                fontSize = 13.sp,
                color = Color(0xFF333745),
                lineHeight = 17.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Right Column: Index Number and Bookmark button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            IconButton(
                onClick = onToggleBookmark,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (card.isBookmarked) Color(0xFFC0392B) else Color(0xFFB0A696),
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = indexNumber,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFB8AE9E),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
