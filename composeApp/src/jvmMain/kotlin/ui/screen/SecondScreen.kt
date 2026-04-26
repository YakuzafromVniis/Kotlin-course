package org.example.project.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.project.component.SecondComponent

@Composable
fun SecondScreen(component: SecondComponent) {
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Вы ввели: ${component.param}", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { component.goBack() }) {
                Text("Назад")
            }
        }
    }
}
