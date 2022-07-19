package co.railgun.spica.ui.main

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.LocalWindowSizeClass
import co.railgun.spica.ui.navigation.LocalNavController
import co.railgun.spica.ui.navigation.Route
import co.railgun.spica.ui.navigation.screen
import co.railgun.spica.ui.theme.SpicaTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Activity.MainUI(
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(this),
    darkTheme: Boolean = isSystemInDarkTheme(),
) {
    SpicaTheme(darkTheme = darkTheme) {
        CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
            MainNavHost()
        }
    }
}

@Composable
private fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Route.splash,
            builder = { screens.forEach { screen -> screen(screen) } },
        )
    }
}
