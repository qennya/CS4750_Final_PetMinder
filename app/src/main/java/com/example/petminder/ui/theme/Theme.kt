package com.example.petminder.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFAEC6EB),      // pastel blue
    secondary = Color(0xFFDDE6F2),
    background = Color(0xFFF8FAFF),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E)
)

@Composable
fun PetMinderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else LightColors,
        typography = Typography(),
        content = content
    )
}
