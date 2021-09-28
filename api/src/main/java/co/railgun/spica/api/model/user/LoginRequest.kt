package co.railgun.spica.api.model.user

import co.railgun.spica.api.model.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("name") val name: String,
    @SerialName("password") val password: String,
    @SerialName("remmember") val remmember: Boolean,
) : Request
