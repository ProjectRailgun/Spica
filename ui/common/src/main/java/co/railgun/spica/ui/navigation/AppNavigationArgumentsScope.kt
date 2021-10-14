package co.railgun.spica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppNavigationArgumentsScope : NavigationArgumentsScope {

    val navController: NavController
        @Composable
        @ReadOnlyComposable
        get() = LocalNavController.current

    fun navArgumentId(): NamedNavArgument =
        navArgument(ARG_ID) { type = NavType.StringType }

    val NavBackStackEntry.argumentId: String
        get() = requireString(ARG_ID)

    companion object : AppNavigationArgumentsScope {
        const val ARG_ID = "id"
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("LocalNavController value not available.")
}
