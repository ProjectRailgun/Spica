package co.railgun.spica.api.model.watch

import co.railgun.spica.api.model.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeFavoriteBangumiStatusRequest(
    @SerialName("status") val status: Int,
) : Request
