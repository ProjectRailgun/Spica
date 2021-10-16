package co.railgun.spica.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberTextFieldState(
    validator: (String) -> Boolean = { true },
    errorFor: (String) -> String = { "" },
): TextFieldState = remember {
    TextFieldState(
        validator = validator,
        errorFor = errorFor,
    )
}

@Stable
class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String) -> String = { "" },
) {
    var text: String by mutableStateOf("")

    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)

    private var displayErrors: Boolean by mutableStateOf(false)

    val isValid: Boolean
        get() = validator(text)

    val error: String?
        get() = when {
            showErrors() -> errorFor(text)
            else -> null
        }

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        if (isFocusedDirty) displayErrors = true
    }

    fun showErrors() = !isValid && displayErrors
}
