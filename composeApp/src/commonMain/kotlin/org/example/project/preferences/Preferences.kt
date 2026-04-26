package org.example.project.preferences

import kotlinx.serialization.Serializable
import androidx.compose.runtime.compositionLocalOf

@Serializable
data class Preferences(
    val theme: ThemePreference = ThemePreference.SYSTEM
) {
    @Serializable
    enum class ThemePreference {
        SYSTEM, LIGHT, DARK
    }
}

// CompositionLocal для удобного доступа к настройкам из любого @Composable
val LocalPreferences = compositionLocalOf { Preferences() }