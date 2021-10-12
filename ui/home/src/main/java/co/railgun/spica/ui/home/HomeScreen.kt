package co.railgun.spica.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen

object HomeScreen : AppScreen {

    override val route: String = "home"

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        HomeUI()
    }
}
