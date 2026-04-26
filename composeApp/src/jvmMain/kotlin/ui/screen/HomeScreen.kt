package org.example.project.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.component.HomeComponent

@Composable
fun HomeScreen(component: HomeComponent) {
    var text by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Введите текст") }
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = { component.navigateToSecondScreen(text) }) {
                Text("Перейти на второй экран")
            }
        }
    }
}