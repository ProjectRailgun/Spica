package co.railgun.spica.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun SpicaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit,
) {
    val currentColors = colors ?: if (isDarkTheme) spicaDarkPalette else spicaLightPalette
    MaterialTheme(
        colors = currentColors,
        content = content,
    )
}

private val spicaDarkPalette = darkColors()

private val spicaLightPalette = lightColors()
