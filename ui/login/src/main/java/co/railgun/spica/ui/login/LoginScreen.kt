package co.railgun.spica.ui.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen

object LoginScreen : AppScreen {

    override val route: String = "login"

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        LoginUI(
            navController = navController,
        )
    }
}
