package co.railgun.spica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface AppNavigationArgumentsScope : NavigationArgumentsScope {

    val navController: NavController
        @Composable
        @ReadOnlyComposable
        get() = LocalNavController.current

    fun navArgumentBangumiId(): NamedNavArgument =
        navArgument(ARG_BANGUMI_ID) { type = NavType.StringType }

    val NavBackStackEntry.bangumiId: String
        get() = requireString(ARG_BANGUMI_ID)

    val navDeepLinkBangumiDetail: NavDeepLink
        get() = navDeepLink {
            uriPattern = "https://bgm.rip/bangumi/${ARG_BANGUMI_ID.routeArgument}"
        }

    fun navArgumentEpisodeId(): NamedNavArgument =
        navArgument(ARG_EPISODE_ID) { type = NavType.StringType }

    val NavBackStackEntry.episodeId: String
        get() = requireString(ARG_EPISODE_ID)

    val navDeepLinkBangumiPlayer: NavDeepLink
        get() = navDeepLink {
            uriPattern = "https://bgm.rip/play/${ARG_EPISODE_ID.routeArgument}"
        }

    companion object : AppNavigationArgumentsScope {
        const val ARG_BANGUMI_ID = "bangumi_id"
        const val ARG_EPISODE_ID = "episode_id"
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("LocalNavController value not available.")
}
