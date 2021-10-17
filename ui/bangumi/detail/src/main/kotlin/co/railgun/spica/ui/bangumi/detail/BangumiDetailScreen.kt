package co.railgun.spica.ui.bangumi.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import co.railgun.spica.ui.navigation.AppScreen
import co.railgun.spica.ui.navigation.Route

object BangumiDetailScreen : AppScreen(route = Route.bangumiDetail) {

    override val arguments: List<NamedNavArgument> = listOf(
        navArgumentBangumiId(),
    )

    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLinkBangumiDetail,
    )

    override val content: @Composable (NavBackStackEntry) -> Unit = { backStackEntry ->
        BangumiDetailUI(
            navController = navController,
            id = backStackEntry.bangumiId,
        )
    }
}
