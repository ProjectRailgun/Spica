package co.railgun.spica.api.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("level") val level: Int,
    @SerialName("email") val email: String,
    @SerialName("email_confirmed") val emailConfirmed: Boolean,
)
