package co.railgun.spica.api.model

import kotlinx.serialization.Serializable

@Serializable(with = ListDataResponseSerializer::class)
sealed class ListDataResponse<out T> : Response {

    data class Ok<out T>(
        val data: List<T>,
        val count: Int = 0,
        val status: Int = 0,
    ) : ListDataResponse<T>()

    data class Error(
        val exception: Throwable,
    ) : ListDataResponse<Nothing>()

    object Unauthorized : ListDataResponse<Nothing>()
}
