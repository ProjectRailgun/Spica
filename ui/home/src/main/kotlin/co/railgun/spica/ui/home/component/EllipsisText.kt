package co.railgun.spica.ui.home.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
internal fun EllipsisText(
    text: String,
    style: TextStyle = TextStyle.Default,
) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
    )
}
