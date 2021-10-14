package co.railgun.spica.ui.home.component

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import co.railgun.spica.data.bangumi.Bangumi
import co.railgun.spica.ui.component.HeightSpacer

internal fun <T> LazyListScope.items(
    title: String,
    items: List<T>,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    item { Title(text = title) }
    items(
        items = items,
        itemContent = itemContent,
    )
    item { HeightSpacer(height = 16.dp) }
}

internal fun LazyListScope.items(
    title: String,
    items: List<Bangumi>,
    onItemClick: () -> Unit,
) {
    item { Title(text = title) }
    items(items = items) { bangumi ->
        Bangumi(
            bangumi = bangumi,
            onclick = onItemClick,
        )
    }
    item { HeightSpacer(height = 16.dp) }
}
