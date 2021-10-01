package co.railgun.spica.ui.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUIState(
    val logged: Boolean = false,
    val error: Error = Error.None,
) {

    sealed class Error {

        object None : Error()

        data class Message(
            val message: String,
        ) : Error()
    }

    companion object {
        val Empty: LoginUIState = LoginUIState()
    }
}
