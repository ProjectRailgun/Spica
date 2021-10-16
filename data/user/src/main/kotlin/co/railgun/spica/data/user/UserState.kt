package co.railgun.spica.data.user

sealed class UserState {

    object Initializing : UserState()

    data class Logged(
        val id: String,
        val name: String,
        val level: Int,
        val email: String,
        val emailConfirmed: Boolean,
    ) : UserState()

    object Unauthorized : UserState()

    data class Error(
        val exception: Throwable,
    ) : UserState()

    data class LoginFailed(
        val message: String,
    ) : UserState()
}
