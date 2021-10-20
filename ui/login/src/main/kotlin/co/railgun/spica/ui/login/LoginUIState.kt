package co.railgun.spica.ui.login

import androidx.compose.runtime.Immutable
import co.railgun.spica.ui.UIError

@Immutable
data class LoginUIState(
    val logged: Boolean = false,
    val loginError: UIError? = null,
) {
    companion object {
        val Empty: LoginUIState = LoginUIState()
    }
}
