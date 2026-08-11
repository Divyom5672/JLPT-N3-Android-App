package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.Category
import com.example.data.seed.InitialData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardBottomSheet(
    categories: List<Category> = InitialData.CATEGORIES,
    initialCategoryId: String? = null,
    onDismiss: () -> Unit,
    onAddCard: (kanji: String, hiragana: String, meaning: String, categoryId: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var kanji by remember { mutableStateOf("") }
    var hiragana by remember { mutableStateOf("") }
    var meaning by remember { mutableStateOf("") }
    var selectedCategory by remember {
        mutableStateOf(
            categories.find { it.id == initialCategoryId } ?: categories.first()
        )
    }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Add Custom N3 Flashcard",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Kanji Input
            OutlinedTextField(
                value = kanji,
                onValueChange = { kanji = it },
                label = { Text("Kanji / Vocabulary (漢字)") },
                placeholder = { Text("e.g., 桜") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_kanji")
            )

            // Hiragana Reading Input
            OutlinedTextField(
                value = hiragana,
                onValueChange = { hiragana = it },
                label = { Text("Hiragana Reading (ひらがな)") },
                placeholder = { Text("e.g., さくら") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_hiragana")
            )

            // English Meaning Input
            OutlinedTextField(
                value = meaning,
                onValueChange = { meaning = it },
                label = { Text("English Meaning") },
                placeholder = { Text("e.g., cherry blossom") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_meaning")
            )

            // Category Selection Dropdown
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = "${selectedCategory.jpName} (${selectedCategory.enName})",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    InitialData.CATEGORIES.forEach { category ->
                        DropdownMenuItem(
                            text = { Text("${category.jpName} (${category.enName})") },
                            onClick = {
                                selectedCategory = category
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        if (kanji.isNotBlank() && meaning.isNotBlank()) {
                            onAddCard(
                                kanji.trim(),
                                if (hiragana.isBlank()) kanji.trim() else hiragana.trim(),
                                meaning.trim(),
                                selectedCategory.id
                            )
                            onDismiss()
                        }
                    },
                    enabled = kanji.isNotBlank() && meaning.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .testTag("save_custom_card_button")
                ) {
                    Text("Save Flashcard")
                }
            }
        }
    }
}
