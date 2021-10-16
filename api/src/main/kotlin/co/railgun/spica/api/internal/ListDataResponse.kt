package co.railgun.spica.api.internal

import co.railgun.spica.api.model.ListDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

internal suspend inline fun <reified T> listDataResponse(
    noinline block: suspend CoroutineScope.() -> ListDataResponse<T>,
): ListDataResponse<T> = runCatching { withContext(Dispatchers.IO, block) }
    .getOrElse { exception ->
        when {
            exception is HttpException && exception.code() == 401 -> ListDataResponse.Unauthorized
            else -> ListDataResponse.Error(exception)
        }
    }
