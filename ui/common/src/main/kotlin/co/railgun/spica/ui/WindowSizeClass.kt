package co.railgun.spica.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWindowSizeClass = staticCompositionLocalOf<WindowSizeClass> {
    error("LocalWindowSizeClass value not available.")
}
