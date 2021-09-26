package co.railgun.api.bgmrip.model.watch

import co.railgun.api.bgmrip.model.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeFavoriteBangumiStatusRequest(
    @SerialName("status") val status: Int,
) : Request
