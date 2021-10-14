package co.railgun.spica.ui.bangumi.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen

object BangumiDetailScreen : AppScreen {

    override val route: String = "bangumi_detail"

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        BangumiDetailUI(
            navController = navController,
        )
    }
}
