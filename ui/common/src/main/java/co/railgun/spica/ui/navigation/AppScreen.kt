package co.railgun.spica.ui.navigation

abstract class AppScreen(
    override val route: String,
) : Screen, AppNavigationArgumentsScope
