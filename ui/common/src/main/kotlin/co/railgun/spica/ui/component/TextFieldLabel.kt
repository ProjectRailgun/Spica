package co.railgun.spica.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TextFieldLabel(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
    )
}
