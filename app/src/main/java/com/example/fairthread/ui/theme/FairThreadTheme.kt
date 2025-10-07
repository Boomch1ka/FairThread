package com.example.fairthread.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.fairthread.ui.theme.Typography
import com.example.fairthread.ui.theme.Shapes

private val DarkColorPalette = darkColors(
    primary = ButtonColor,
    onPrimary = ButtonTextColor,
    surface = ButtonColor,
    onSurface = ButtonTextColor
)

private val LightColorPalette = lightColors(
    primary = ButtonColor,
    onPrimary = ButtonTextColor,
    surface = ButtonColor,
    onSurface = ButtonTextColor
)

@Composable
fun FairThreadTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography, // You can define this in Typography.kt
        shapes = Shapes,         // Optional: define in Shapes.kt
        content = content
    )
}