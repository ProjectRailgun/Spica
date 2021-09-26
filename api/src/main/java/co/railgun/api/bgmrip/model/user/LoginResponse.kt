package co.railgun.api.bgmrip.model.user

import kotlinx.serialization.Serializable

@Serializable(with = LoginResponseSerializer::class)
sealed class LoginResponse {

    @Serializable
    object Success : LoginResponse()

    @Serializable
    data class Failure(
        val message: String,
    ) : LoginResponse()
}
