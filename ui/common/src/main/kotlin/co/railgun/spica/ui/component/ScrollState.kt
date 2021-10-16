package co.railgun.spica.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun ProvideScrollState(
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable () -> Unit,
) = CompositionLocalProvider(
    LocalScrollState provides scrollState,
    content = content,
)

val LocalScrollState = staticCompositionLocalOf<ScrollState> {
    error("LocalScrollState value not available.")
}
