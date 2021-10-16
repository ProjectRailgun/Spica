package co.railgun.spica.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemTitle(
    text: String,
) {
    Text(
        modifier = run {
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp,
                )
        },
        text = text,
    )
}
