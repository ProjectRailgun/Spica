package co.railgun.spica.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateToBangumiDetail(id: String) =
    navigate(Route.bangumiDetail(id)) {
        launchSingleTop = true
    }

fun NavController.navigateToBangumiPlayer(id: String) =
    navigate(Route.bangumiPlayer(id)) {
        launchSingleTop = true
    }

fun NavController.navigateToHome(popBackStack: Boolean = true) {
    if (popBackStack) popBackStack()
    navigate(Route.home) {
        launchSingleTop = true
    }
}

fun NavController.navigateToLogin(popBackStack: Boolean = true) {
    if (popBackStack) popBackStack()
    navigate(Route.login) {
        launchSingleTop = true
    }
}
