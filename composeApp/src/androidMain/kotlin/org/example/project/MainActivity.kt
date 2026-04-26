package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import org.example.project.component.RootComponentImpl
import org.example.project.util.InjectionCompanion // Импорт твоего синглтона с HttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Делает интерфейс "на весь экран" (под строку состояния)
        enableEdgeToEdge()

        // Создаем "мозги" приложения.
        // Передаем HttpClient из InjectionCompanion, чтобы интернет работал везде.
        val root = RootComponentImpl(
            httpClient = InjectionCompanion.httpClient,
            componentContext = defaultComponentContext()
        )

        setContent {
            // Запускаем главный UI и передаем ему наш RootComponent
            App(root)
        }
    }
}