package co.railgun.spica.ui.bangumi.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen
import co.railgun.spica.ui.navigation.Route

object BangumiDetailScreen : AppScreen(route = Route.bangumiDetail) {

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        BangumiDetailUI(
            navController = navController,
        )
    }
}
