package co.railgun.spica.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateToBangumiDetail() {
    navigate(Route.bangumiDetail)
}

fun NavController.navigateToHome(popBackStack: Boolean = true) {
    if (popBackStack) popBackStack()
    navigate(Route.home)
}

fun NavController.navigateToLogin(popBackStack: Boolean = true) {
    if (popBackStack) popBackStack()
    navigate(Route.login)
}
