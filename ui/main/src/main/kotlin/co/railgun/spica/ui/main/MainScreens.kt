package co.railgun.spica.ui.main

import co.railgun.spica.ui.bangumi.detail.BangumiDetailScreen
import co.railgun.spica.ui.bangumi.player.BangumiPlayerScreen
import co.railgun.spica.ui.home.HomeScreen
import co.railgun.spica.ui.login.LoginScreen
import co.railgun.spica.ui.navigation.Screen
import co.railgun.spica.ui.splash.SplashScreen

internal val screens: Set<Screen> = setOf(
    BangumiDetailScreen,
    BangumiPlayerScreen,
    HomeScreen,
    LoginScreen,
    SplashScreen,
)
