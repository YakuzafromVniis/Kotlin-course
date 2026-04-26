package org.example.project.ui.util

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

fun Modifier.adaptiveHorizontalPadding(windowSizeClass: WindowSizeClass): Modifier {
    val paddingDp = when {
        windowSizeClass.windowWidthSizeClass == androidx.window.core.layout.WindowWidthSizeClass.EXPANDED -> 120.dp
        windowSizeClass.windowWidthSizeClass == androidx.window.core.layout.WindowWidthSizeClass.MEDIUM -> 64.dp
        else -> 8.dp // Для телефонов (Compact)
    }
    return this.padding(horizontal = paddingDp)
}