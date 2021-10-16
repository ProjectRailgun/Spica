package co.railgun.spica.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        WidthSpacer(width = 16.dp)
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error),
        )
    }
}
