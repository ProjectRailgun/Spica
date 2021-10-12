package co.railgun.spica.ui.splash

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen

object SplashScreen : AppScreen {

    override val route: String = "splash"

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        SplashUI(
            navController = navController,
        )
    }
}
