package co.railgun.spica.ui.login

sealed class LoginAction {

    data class Login(
        val username: String,
        val password: String,
    ) : LoginAction()

    object Logged : LoginAction()
}
