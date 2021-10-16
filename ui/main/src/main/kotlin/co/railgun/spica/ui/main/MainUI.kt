package co.railgun.spica.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.bangumi.detail.BangumiDetailScreen
import co.railgun.spica.ui.bangumi.player.BangumiPlayerScreen
import co.railgun.spica.ui.home.HomeScreen
import co.railgun.spica.ui.login.LoginScreen
import co.railgun.spica.ui.navigation.LocalNavController
import co.railgun.spica.ui.navigation.Route
import co.railgun.spica.ui.navigation.screen
import co.railgun.spica.ui.splash.SplashScreen
import co.railgun.spica.ui.theme.SpicaTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainUI(
    systemUiController: SystemUiController = rememberSystemUiController(),
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !isDarkTheme,
            isNavigationBarContrastEnforced = false,
        )
    }
    SpicaTheme(
        isDarkTheme = isDarkTheme,
        content = { MainNavHost() }
    )
}

@Composable
private fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        ProvideWindowInsets {
            NavHost(
                navController = navController,
                startDestination = Route.splash,
            ) {
                screen(BangumiDetailScreen)
                screen(BangumiPlayerScreen)
                screen(HomeScreen)
                screen(LoginScreen)
                screen(SplashScreen)
            }
        }
    }
}