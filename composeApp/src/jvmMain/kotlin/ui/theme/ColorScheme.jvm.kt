package org.example.project.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

@Composable
@ReadOnlyComposable
actual fun getApplicationColorScheme(useDarkTheme: Boolean): ColorScheme =
    if(useDarkTheme) darkColorScheme() else lightColorScheme()