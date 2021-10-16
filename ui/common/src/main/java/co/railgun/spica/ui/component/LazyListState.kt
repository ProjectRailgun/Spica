package co.railgun.spica.ui.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun ProvideLazyListState(
    lazyListState: LazyListState = rememberLazyListState(),
    content: @Composable () -> Unit,
) = CompositionLocalProvider(
    LocalLazyListState provides lazyListState,
    content = content,
)

val LocalLazyListState = staticCompositionLocalOf<LazyListState> {
    error("LocalLazyListState value not available.")
}
