package co.railgun.spica.api.model

import kotlinx.serialization.Serializable

@Serializable(with = DataResponseSerializer::class)
sealed class DataResponse<out T> : Response {

    data class Ok<out T>(
        val data: T,
    ) : DataResponse<T>()

    data class Error(
        val exception: Throwable,
    ) : DataResponse<Nothing>()

    object Unauthorized : DataResponse<Nothing>()
}
