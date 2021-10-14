package co.railgun.spica.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen
import co.railgun.spica.ui.navigation.Route

object HomeScreen : AppScreen(route = Route.home) {

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        HomeUI(
            navController = navController,
        )
    }
}
