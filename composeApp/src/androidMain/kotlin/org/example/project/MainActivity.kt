package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import org.example.project.component.RootComponentImpl
import org.example.project.util.InjectionCompanion
import org.example.project.AndroidInjectionCompanion // Импорт для DataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Получаем DataStore (настройки), передавая контекст активити
        val dataStore = AndroidInjectionCompanion.getDataStore(this)

        // 2. Создаем RootComponent и передаем в него ВСЕ зависимости:
        // - HttpClient (интернет)
        // - DataStore (настройки)
        // - ComponentContext (навигация)
        val root = RootComponentImpl(
            httpClient = InjectionCompanion.httpClient,
            dataStore = dataStore,
            componentContext = defaultComponentContext()
        )

        setContent {
            // Запускаем главный UI
            App(root)
        }
    }
}