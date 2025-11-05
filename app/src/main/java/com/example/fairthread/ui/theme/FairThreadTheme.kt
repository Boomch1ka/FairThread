package com.example.fairthread.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = Black,
    background = Gold,
    surface = Black,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = Gold,
    onPrimary = White,
    background = White,
    surface = White,
    onSurface = Black
)

@Composable
fun FairThreadTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}