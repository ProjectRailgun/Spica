@file:Suppress("unused")

package co.railgun.spica.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

val Colors.topAppBarBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = MaterialTheme.colors.surface.copy(alpha = 0.9f)
