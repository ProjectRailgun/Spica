package co.railgun.api.bgmrip.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal interface Response

@Serializable
abstract class DataResponse<T> : Response {

    @SerialName("data")
    abstract val data: T
}

@Serializable
data class ListDataResponse<T>(
    @SerialName("data") override val data: List<T>,
    @SerialName("count") val count: Int = 0,
    @SerialName("status") val status: Int = 0,
) : DataResponse<List<T>>()
