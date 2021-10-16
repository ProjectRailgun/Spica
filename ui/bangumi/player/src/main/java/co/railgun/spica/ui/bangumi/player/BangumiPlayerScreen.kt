package co.railgun.spica.ui.bangumi.player

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import co.railgun.spica.ui.navigation.AppScreen
import co.railgun.spica.ui.navigation.Route

object BangumiPlayerScreen : AppScreen(route = Route.bangumiPlayer) {

    override val arguments: List<NamedNavArgument> = listOf(
        navArgumentEpisodeId(),
    )

    override val content: @Composable (NavBackStackEntry) -> Unit = { backStackEntry ->
        BangumiPlayUI(
            navController = navController,
            id = backStackEntry.episodeId,
        )
    }
}
