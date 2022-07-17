package co.railgun.spica.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import co.railgun.spica.ui.common.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    textFieldState: TextFieldState,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
) {
    var showPassword: Boolean by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = textFieldState.text,
        onValueChange = {
            textFieldState.text = it
            textFieldState.enableShowErrors()
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                textFieldState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) textFieldState.enableShowErrors()
            },
        textStyle = MaterialTheme.typography.body2,
        label = { TextFieldLabel(text = label) },
        trailingIcon = {
            IconButton(
                onClick = { showPassword = !showPassword },
            ) {
                Icon(
                    imageVector = when {
                        showPassword -> Icons.Filled.Visibility
                        else -> Icons.Filled.VisibilityOff
                    },
                    contentDescription = stringResource(
                        id = when {
                            showPassword -> R.string.hide_password
                            else -> R.string.show_password
                        },
                    ),
                )
            }
        },
        visualTransformation = when {
            showPassword -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
        isError = textFieldState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            },
        ),
        singleLine = true,
    )
    textFieldState.error?.let { error -> TextFieldError(textError = error) }
}
