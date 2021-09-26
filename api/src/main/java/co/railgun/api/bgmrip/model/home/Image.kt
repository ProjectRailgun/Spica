package co.railgun.api.bgmrip.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("url") val url: String,
    @SerialName("dominant_color") val dominantColor: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
)
