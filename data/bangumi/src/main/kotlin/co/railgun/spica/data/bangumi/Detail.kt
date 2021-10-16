package co.railgun.spica.data.bangumi

sealed class Detail<T> {

    data class Result<T>(
        val data: T,
    ) : Detail<T>()

    object Unauthorized : Detail<Nothing>()

    data class Error<T>(
        val exception: Throwable,
    ) : Detail<T>()
}
