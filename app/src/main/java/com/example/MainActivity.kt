package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.Category
import com.example.ui.screens.CategorySelectScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.SectionDetailScreen
import com.example.ui.screens.StatsScreen
import com.example.ui.screens.WeekSelectScreen
import com.example.ui.theme.JLPTN3Theme
import com.example.ui.viewmodel.FlashcardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JLPTN3Theme {
                JlptN3App()
            }
        }
    }
}

@Composable
fun JlptN3App(
    viewModel: FlashcardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Home, 1 = Library, 2 = Stats
    var selectedSection by remember { mutableStateOf<String?>(null) } // "kanji" or "vocab"
    var selectedWeek by remember { mutableStateOf<Int?>(null) } // 1..6 or 1..8
    var activeCategory by remember { mutableStateOf<Category?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 8.dp,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0 && selectedSection == null && selectedWeek == null && activeCategory == null,
                    onClick = {
                        selectedTab = 0
                        selectedSection = null
                        selectedWeek = null
                        activeCategory = null
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    modifier = Modifier.testTag("tab_home"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Library Search") },
                    label = { Text("Library") },
                    modifier = Modifier.testTag("tab_library"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    )
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                    },
                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Study Progress Stats") },
                    label = { Text("Stats") },
                    modifier = Modifier.testTag("tab_stats"),
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    )
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> {
                    val currentCat = activeCategory
                    val currentWeek = selectedWeek
                    val currentSection = selectedSection
                    if (currentCat != null) {
                        SectionDetailScreen(
                            category = currentCat,
                            state = uiState,
                            viewModel = viewModel,
                            onBackToCategories = {
                                activeCategory = null
                            }
                        )
                    } else if (currentSection != null && currentWeek != null) {
                        CategorySelectScreen(
                            sectionType = currentSection,
                            weekNumber = currentWeek,
                            allCards = uiState.allCards,
                            onCategorySelected = { cat ->
                                viewModel.selectCategory(cat.id)
                                viewModel.randomizeDeck()
                                activeCategory = cat
                            },
                            onBackToWeeks = {
                                selectedWeek = null
                            }
                        )
                    } else if (currentSection != null) {
                        WeekSelectScreen(
                            sectionType = currentSection,
                            allCards = uiState.allCards,
                            onSelectWeek = { weekNum ->
                                selectedWeek = weekNum
                            },
                            onBackToHome = {
                                selectedSection = null
                            }
                        )
                    } else {
                        HomeScreen(
                            allCards = uiState.allCards,
                            onSelectSection = { section ->
                                selectedSection = section
                                selectedWeek = null
                                activeCategory = null
                            }
                        )
                    }
                }

                1 -> LibraryScreen(state = uiState, viewModel = viewModel)
                2 -> StatsScreen(state = uiState, viewModel = viewModel)
            }
        }
    }
}
