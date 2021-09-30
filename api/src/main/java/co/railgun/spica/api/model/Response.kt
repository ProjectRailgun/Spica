package co.railgun.spica.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal interface Response

@Serializable
data class ListDataResponse<T>(
    @SerialName("data") val data: List<T>,
    @SerialName("count") val count: Int = 0,
    @SerialName("status") val status: Int = 0,
) : Response
