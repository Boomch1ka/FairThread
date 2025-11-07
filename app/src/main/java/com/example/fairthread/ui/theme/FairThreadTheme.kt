package com.example.fairthread.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Gold,
    onPrimary = Black,
    background = Gold,
    surface = Black,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = Gold,
    onPrimary = Black,
    background = White,
    surface = White,
    onSurface = Black
)

@Composable
fun FairThreadTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
