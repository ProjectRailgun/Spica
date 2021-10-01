package co.railgun.spica.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SpicaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit,
) {
    val currentColors = colors ?: if (isDarkTheme) spicaDarkPalette else spicaLightPalette
    MaterialTheme(
        colors = currentColors,
        shapes = Shapes,
        content = content,
    )
}

private val spicaDarkPalette = darkColors()

private val spicaLightPalette = lightColors(
    primary = Color(0xFF0077C2),
    secondary = Color(0xFF4DD0E1),
)
