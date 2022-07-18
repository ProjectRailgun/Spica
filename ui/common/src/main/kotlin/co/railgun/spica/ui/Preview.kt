package co.railgun.spica.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.navigation.LocalNavController
import co.railgun.spica.ui.theme.SpicaTheme

@Composable
fun ProvideSpicaPreviewContainer(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    navController: NavController = rememberNavController(),
    content: @Composable () -> Unit,
) {
    SpicaTheme(
        isDarkTheme = isDarkTheme,
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController,
            content = content,
        )
    }
}
