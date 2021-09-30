package co.railgun.spica.api.internal

import co.railgun.spica.api.model.DataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

internal suspend inline fun <reified T> dataResponse(
    noinline block: suspend CoroutineScope.() -> DataResponse<T>,
): DataResponse<T> = runCatching { withContext(Dispatchers.IO, block) }
    .getOrElse { exception ->
        when {
            exception is HttpException && exception.code() == 401 -> DataResponse.Unauthorized
            else -> DataResponse.Error(exception)
        }
    }
