package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.ui.theme.getApplicationColorScheme
import org.example.project.ui.util.adaptiveHorizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()
    val windowInfo = currentWindowAdaptiveInfo() // Адаптивность

    // Состояния данных
    val shoppingList = remember { mutableStateListOf<String>("Молоко", "Хлеб") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    MaterialTheme(colorScheme = getApplicationColorScheme()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Список покупок") },
                    actions = {
                        // Выпадающее меню
                        var menuExpanded by remember { mutableStateOf(false) }
                        Box {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(Icons.Default.MoreVert, "Меню")
                            }
                            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                                DropdownMenuItem(
                                    text = { Text("Очистить всё") },
                                    leadingIcon = { Icon(Icons.Default.DeleteSweep, null) },
                                    onClick = {
                                        menuExpanded = false
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                // Кнопка, которая сворачивается при скролле вниз
                ExtendedFloatingActionButton(
                    onClick = { /* Логика добавления */ },
                    icon = { Icon(Icons.Default.Add, null) },
                    text = { Text("Добавить") },
                    expanded = !lazyListState.lastScrolledForward
                )
            }
        ) { paddingValues ->

            // Основной контент с адаптивными отступами
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .adaptiveHorizontalPadding(windowInfo.windowSizeClass)
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(shoppingList) { index, item ->
                        Card(
                            onClick = {
                                scope.launch { snackbarHostState.showSnackbar("Выбрано: $item") }
                            }
                        ) {
                            ListItem(
                                headlineContent = { Text(item) },
                                trailingContent = {
                                    IconButton(onClick = { shoppingList.removeAt(index) }) {
                                        Icon(Icons.Default.Delete, "Удалить")
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Модальный диалог подтверждения
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            shoppingList.clear()
                            showDeleteDialog = false
                        }) { Text("Да, удалить") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) { Text("Отмена") }
                    },
                    title = { Text("Удалить весь список?") },
                    icon = { Icon(Icons.Default.Warning, null) }
                )
            }
        }
    }
}