@file:Suppress("unused")

package co.railgun.spica.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val ColorScheme.topAppBarBackground
    @Composable get() = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
