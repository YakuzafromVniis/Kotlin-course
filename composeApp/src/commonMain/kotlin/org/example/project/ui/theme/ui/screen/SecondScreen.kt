package org.example.project.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.component.RequestState
import org.example.project.component.SecondComponent

@Composable
fun SecondScreen(component: SecondComponent) {
    Scaffold(
        topBar = {
            Text("Результат запроса", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            // Проверяем состояние из нашего компонента
            when (val state = component.requestState) {
                is RequestState.Loading -> {
                    // Показываем индикатор загрузки
                    CircularProgressIndicator()
                }

                is RequestState.Error -> {
                    // Показываем ошибку
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Error, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                        Text("Не удалось загрузить данные")
                        Button(onClick = { /* Здесь можно вызвать метод перезагрузки */ }) {
                            Text("Назад")
                        }
                    }
                }

                is RequestState.Success -> {
                    // Показываем результат от сервера
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Ответ сервера:", style = MaterialTheme.typography.labelLarge)
                        Spacer(Modifier.height(8.dp))
                        Text(state.value)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { component.goBack() }) {
                            Text("Вернуться")
                        }
                    }
                }
            }
        }
    }
}