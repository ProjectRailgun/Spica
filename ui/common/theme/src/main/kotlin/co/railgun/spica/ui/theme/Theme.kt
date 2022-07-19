package co.railgun.spica.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SpicaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && isDynamicColorSupported -> when {
            darkTheme -> dynamicDarkColorScheme(LocalContext.current)
            else -> dynamicLightColorScheme(LocalContext.current)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = !darkTheme,
                isNavigationBarContrastEnforced = false,
            )
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        content = content,
    )
}

val isDynamicColorSupported: Boolean
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0077C2),
    secondary = Color(0xFF4DD0E1),
)
