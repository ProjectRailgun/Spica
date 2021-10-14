package co.railgun.spica.ui.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.AppBarDefaults
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun LazyListState.liftOnScroll(
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
): Dp = when (firstVisibleItemScrollOffset) {
    0 -> 0.dp
    else -> elevation
}
