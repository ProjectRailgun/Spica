package co.railgun.spica.ui.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen
import co.railgun.spica.ui.navigation.Route

object LoginScreen : AppScreen(route = Route.login) {

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        LoginUI(
            navController = navController,
        )
    }
}
