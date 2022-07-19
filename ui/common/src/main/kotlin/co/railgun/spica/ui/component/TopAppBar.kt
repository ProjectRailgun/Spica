package co.railgun.spica.ui.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun LazyListState.liftOnScroll(
    elevation: Dp = 3.dp,
): Dp = when (firstVisibleItemScrollOffset) {
    0 -> 0.dp
    else -> elevation
}
