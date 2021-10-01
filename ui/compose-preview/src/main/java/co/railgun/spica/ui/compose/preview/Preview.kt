package co.railgun.spica.ui.compose.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.LocalNavController
import co.railgun.spica.ui.theme.SpicaTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun ProvideSpicaPreviewContainer(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    navController: NavController = rememberNavController(),
    consumeWindowInsets: Boolean = true,
    content: @Composable () -> Unit,
) {
    SpicaTheme(
        isDarkTheme = isDarkTheme,
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController,
        ) {
            ProvideWindowInsets(
                consumeWindowInsets = consumeWindowInsets,
                content = content
            )
        }
    }
}
