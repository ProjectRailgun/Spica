package co.railgun.spica.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

interface Screen {
    val route: String
    val arguments: List<NamedNavArgument> get() = emptyList()
    val deepLinks: List<NavDeepLink> get() = emptyList()
    val content: @Composable (NavBackStackEntry) -> Unit
}

fun NavGraphBuilder.screen(screen: Screen) {
    with(screen) {
        composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            content = content
        )
    }
}
