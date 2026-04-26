package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// 1. ПРОВЕРЬ ЭТИ ИМПОРТЫ (это расширения Decompose)
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

// 2. ПРОВЕРЬ ПУТИ К ТВОИМ ФАЙЛАМ
// Если твои папки называются иначе, измени эти строки:
import org.example.project.component.RootComponent
import org.example.project.ui.screen.HomeScreen
import org.example.project.ui.screen.SecondScreen

@Composable
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        // Если 'Children' горит красным, значит Шаг 1 не выполнен или не нажат Sync
        Children(
            stack = rootComponent.childStack,
            animation = stackAnimation(fade() + slide())
        ) { child -> // Мы явно указали имя 'child'
            val instance = child.instance // Теперь 'instance' будет виден

            when (instance) {
                is RootComponent.Child.Home -> HomeScreen(instance.component)
                is RootComponent.Child.Second -> SecondScreen(instance.component)
            }
        }
    }
}