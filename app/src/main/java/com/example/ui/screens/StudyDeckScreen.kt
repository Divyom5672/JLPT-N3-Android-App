package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.seed.InitialData
import com.example.ui.components.CategoryChip
import com.example.ui.components.SwipeableCardDeck
import com.example.ui.viewmodel.FlashcardUiState
import com.example.ui.viewmodel.FlashcardViewModel

@Composable
fun StudyDeckScreen(
    state: FlashcardUiState,
    viewModel: FlashcardViewModel,
    onGoBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val totalDeckSize = state.deckCards.size
    val currentCardNum = if (totalDeckSize > 0) state.currentDeckIndex.coerceAtMost(totalDeckSize) else 0
    val progressFraction = if (totalDeckSize > 0) (state.currentDeckIndex.toFloat() / totalDeckSize.toFloat()).coerceIn(0f, 1f) else 0f

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Progress Header Bar
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DECK PROGRESS",
                        style = MaterialTheme.typography.labelSmall,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "$currentCardNum / $totalDeckSize Cards",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            }
        }

        // Interactive Swipeable Deck Component
        SwipeableCardDeck(
            deckCards = state.deckCards,
            currentIndex = state.currentDeckIndex,
            canUndo = state.canUndo,
            onSwipeRightMastered = { card -> viewModel.swipeRightMastered(card) },
            onSwipeLeftReview = { card -> viewModel.swipeLeftReview(card) },
            onUndo = { viewModel.undoSwipe() },
            onToggleBookmark = { card -> viewModel.toggleBookmark(card) },
            onShuffleDeck = { viewModel.shuffleDeck() },
            onGoBack = {
                if (onGoBack != null) {
                    onGoBack()
                } else {
                    viewModel.selectCategory(null)
                }
            },
            onTryAgain = {
                viewModel.resetDeckProgress()
                viewModel.shuffleDeck()
            },
            modifier = Modifier.weight(1f)
        )
    }
}
