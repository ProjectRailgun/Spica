package co.railgun.spica.ui.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import co.railgun.spica.ui.theme.SpicaTheme

@Composable
fun MainUI(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    SpicaTheme(
        isDarkTheme = isDarkTheme,
    ) {

    }
}
