package com.example.fairthread.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Gold,
    onPrimary = Black,
    background = Gold,
    surface = Black,
    onSurface = White
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Gold,
    onPrimary = White,
    background = White,
    surface = White,
    onSurface = Black
)

@Composable
fun FairThreadTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}