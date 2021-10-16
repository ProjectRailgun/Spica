package co.railgun.spica.ui.component

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun TextFieldLabel(
    text: String,
) {
    CompositionLocalProvider(
        LocalContentAlpha provides ContentAlpha.medium,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
        )
    }
}
