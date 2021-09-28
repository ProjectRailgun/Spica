package co.railgun.api.bgmrip.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal interface Response

@Serializable
data class DataResponse<T>(
    @SerialName("data") val data: T,
) : Response

@Serializable
data class ListDataResponse<T>(
    @SerialName("data") val data: List<T>,
    @SerialName("count") val count: Int = 0,
    @SerialName("status") val status: Int = 0,
) : Response
