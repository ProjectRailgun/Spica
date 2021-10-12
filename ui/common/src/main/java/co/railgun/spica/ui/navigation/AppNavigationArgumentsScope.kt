package co.railgun.spica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

interface AppNavigationArgumentsScope : NavigationArgumentsScope {

    val navController: NavController
        @Composable
        @ReadOnlyComposable
        get() = LocalNavController.current
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("LocalNavController value not available.")
}
