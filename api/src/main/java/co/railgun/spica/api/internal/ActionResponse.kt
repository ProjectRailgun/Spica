package co.railgun.spica.api.internal

import co.railgun.spica.api.model.ActionResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

internal suspend inline fun actionResponse(
    noinline block: suspend CoroutineScope.() -> ActionResponse,
): ActionResponse = runCatching { withContext(Dispatchers.IO, block) }
    .getOrElse {
        when (it) {
            is HttpException -> it.decodeErrorBody()
            else -> ActionResponse.Message(message = it.message ?: "Unknown error.")
        }
    }
