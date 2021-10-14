package co.railgun.spica.ui.splash

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen
import co.railgun.spica.ui.navigation.Route

object SplashScreen : AppScreen(route = Route.splash) {

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        SplashUI(
            navController = navController,
        )
    }
}
