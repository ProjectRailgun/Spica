package co.railgun.spica.ui.home.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import co.railgun.spica.data.bangumi.Bangumi
import co.railgun.spica.ui.component.HeightSpacer

internal fun LazyListScope.items(
    title: String,
    items: List<Bangumi>,
    onItemClick: Bangumi.() -> Unit,
) {
    item { Title(text = title) }
    items(items = items) { bangumi ->
        Bangumi(
            bangumi = bangumi,
            onclick = { bangumi.onItemClick() },
        )
    }
    item { HeightSpacer(height = 16.dp) }
}
