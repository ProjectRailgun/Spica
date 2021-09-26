package co.railgun.api.bgmrip.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal interface Response

@Serializable
abstract class DataResponse<T> : Response {

    @SerialName("data")
    abstract val data: T
}
