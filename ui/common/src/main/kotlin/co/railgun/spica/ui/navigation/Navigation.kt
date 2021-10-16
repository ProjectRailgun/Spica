package co.railgun.spica.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateToBangumiDetail(id: String) =
    navigate(Route.bangumiDetail(id))

fun NavController.navigateToBangumiPlayer(id: String) =
    navigate(Route.bangumiPlayer(id))

fun NavController.navigateToHome(popBackStack: Boolean = true) {
    if (popBackStack) popBackStack()
    navigate(Route.home)
}

fun NavController.navigateToLogin(popBackStack: Boolean = true) {
    if (popBackStack) popBackStack()
    navigate(Route.login)
}
