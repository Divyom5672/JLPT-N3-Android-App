package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FlashcardEntity
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun SwipeableCardDeck(
    deckCards: List<FlashcardEntity>,
    currentIndex: Int,
    canUndo: Boolean,
    onSwipeRightMastered: (FlashcardEntity) -> Unit,
    onSwipeLeftReview: (FlashcardEntity) -> Unit,
    onUndo: () -> Unit,
    onToggleBookmark: (FlashcardEntity) -> Unit,
    onShuffleDeck: () -> Unit,
    onGoBack: () -> Unit,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val activeCard = deckCards.getOrNull(currentIndex)
    val nextCard = deckCards.getOrNull(currentIndex + 1)
    val thirdCard = deckCards.getOrNull(currentIndex + 2)

    // Drag offset & Rotation state
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    // 3D Flip state
    var isFlipped by remember(currentIndex) { mutableStateOf(false) }
    val flipRotationAnim = remember(currentIndex) { Animatable(0f) }

    // Swiping threshold
    val swipeThreshold = 300f

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (activeCard == null) {
            // Empty Deck / Completed State
            DeckCompletedCard(
                totalCards = deckCards.size,
                onGoBack = onGoBack,
                onTryAgain = onTryAgain
            )
        } else {
            // Card Deck Stack Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background Card 2
                if (thirdCard != null) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(0.90f)
                            .offset(y = 24.dp)
                            .alpha(0.5f)
                    ) {}
                }

                // Background Card 1
                if (nextCard != null) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(0.95f)
                            .offset(y = 12.dp)
                            .alpha(0.8f)
                    ) {
                        FlashcardFront(
                            card = nextCard,
                            onToggleBookmark = {}
                        )
                    }
                }

                // Active Front Card with Drag & 3D Flip Physics
                val currentOffsetX = offsetX.value
                val rotationAngle = (currentOffsetX / 25f).coerceIn(-25f, 25f)
                val isRightSwipe = currentOffsetX > 0
                val overlayAlpha = (abs(currentOffsetX) / swipeThreshold).coerceIn(0f, 1f)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(currentOffsetX.roundToInt(), offsetY.value.roundToInt()) }
                        .graphicsLayer {
                            rotationZ = rotationAngle
                            rotationY = flipRotationAnim.value
                            cameraDistance = 12 * density
                        }
                        .pointerInput(activeCard.id) {
                            detectDragGestures(
                                onDragEnd = {
                                    coroutineScope.launch {
                                        if (offsetX.value > swipeThreshold) {
                                            // Swipe Right -> Mastered
                                            offsetX.animateTo(1200f, tween(250))
                                            onSwipeRightMastered(activeCard)
                                            offsetX.snapTo(0f)
                                            offsetY.snapTo(0f)
                                        } else if (offsetX.value < -swipeThreshold) {
                                            // Swipe Left -> Review
                                            offsetX.animateTo(-1200f, tween(250))
                                            onSwipeLeftReview(activeCard)
                                            offsetX.snapTo(0f)
                                            offsetY.snapTo(0f)
                                        } else {
                                            // Spring Back
                                            launch { offsetX.animateTo(0f, spring(stiffness = Spring.StiffnessMediumLow)) }
                                            launch { offsetY.animateTo(0f, spring(stiffness = Spring.StiffnessMediumLow)) }
                                        }
                                    }
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    coroutineScope.launch {
                                        offsetX.snapTo(offsetX.value + dragAmount.x)
                                        offsetY.snapTo(offsetY.value + dragAmount.y)
                                    }
                                }
                            )
                        }
                        .clickable {
                            coroutineScope.launch {
                                isFlipped = !isFlipped
                                val target = if (isFlipped) 180f else 0f
                                flipRotationAnim.animateTo(
                                    targetValue = target,
                                    animationSpec = tween(
                                        durationMillis = 350,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        }
                ) {
                    if (flipRotationAnim.value <= 90f) {
                        FlashcardFront(
                            card = activeCard,
                            onToggleBookmark = { onToggleBookmark(activeCard) }
                        )
                    } else {
                        // Render Back side (injected graphicsLayer rotation inversion to prevent reverse text rendering)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer { rotationY = 180f }
                        ) {
                            FlashcardBack(
                                card = activeCard,
                                onToggleBookmark = { onToggleBookmark(activeCard) }
                            )
                        }
                    }

                    // Swipe Right Green "GOT IT" Badge Overlay
                    if (currentOffsetX > 20f) {
                        Surface(
                            color = com.example.ui.theme.MasteredBg.copy(alpha = 0.9f * overlayAlpha),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(overlayAlpha)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Surface(
                                    color = com.example.ui.theme.MasteredBg,
                                    shape = RoundedCornerShape(16.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.MasteredBorder),
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = com.example.ui.theme.MasteredText,
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text(
                                            text = "覚えた (GOT IT!)",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = com.example.ui.theme.MasteredText
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Swipe Left Crimson "NEEDS REVIEW" Badge Overlay
                    if (currentOffsetX < -20f) {
                        Surface(
                            color = com.example.ui.theme.ReviewBg.copy(alpha = 0.9f * overlayAlpha),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(overlayAlpha)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Surface(
                                    color = com.example.ui.theme.ReviewBg,
                                    shape = RoundedCornerShape(16.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.ReviewBorder),
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = com.example.ui.theme.ReviewText,
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text(
                                            text = "復習 (REVIEW)",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = com.example.ui.theme.ReviewText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Action Bar: Quick Swipe Buttons + Utilities
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Button: Review / Red Swipe
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            offsetX.animateTo(-1200f, tween(250))
                            onSwipeLeftReview(activeCard)
                            offsetX.snapTo(0f)
                            offsetY.snapTo(0f)
                        }
                    },
                    modifier = Modifier
                        .testTag("swipe_review_button")
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(com.example.ui.theme.ReviewBg)
                        .border(1.5.dp, com.example.ui.theme.ReviewBorder, RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Needs Review",
                        tint = com.example.ui.theme.ReviewText,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Middle Utilities: Undo, Shuffle
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Undo button
                    IconButton(
                        onClick = onUndo,
                        enabled = canUndo,
                        modifier = Modifier
                            .testTag("undo_button")
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                if (canUndo) MaterialTheme.colorScheme.surfaceVariant
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Undo,
                            contentDescription = "Undo Last Swipe",
                            tint = if (canUndo) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Shuffle Button
                    IconButton(
                        onClick = onShuffleDeck,
                        modifier = Modifier
                            .testTag("shuffle_deck_button")
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle Deck",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Right Button: Mastered / Green Swipe
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            offsetX.animateTo(1200f, tween(250))
                            onSwipeRightMastered(activeCard)
                            offsetX.snapTo(0f)
                            offsetY.snapTo(0f)
                        }
                    },
                    modifier = Modifier
                        .testTag("swipe_mastered_button")
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(com.example.ui.theme.MasteredBg)
                        .border(1.5.dp, com.example.ui.theme.MasteredBorder, RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Got It / Mastered",
                        tint = com.example.ui.theme.MasteredText,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DeckCompletedCard(
    totalCards: Int,
    onGoBack: () -> Unit,
    onTryAgain: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F5E9))
            ) {
                Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "お疲れ様でした！",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = "You completed all $totalCards cards in this deck!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onGoBack,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("deck_completed_go_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Text("Go Back", modifier = Modifier.padding(start = 6.dp))
                }

                Button(
                    onClick = onTryAgain,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("deck_completed_try_again_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Text("Try Again", modifier = Modifier.padding(start = 6.dp))
                }
            }
        }
    }
}
