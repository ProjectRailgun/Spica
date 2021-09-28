@file:Suppress("unused")

package co.railgun.spica.api.function.user

import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.internal.userService
import co.railgun.spica.api.model.user.LoginRequest
import co.railgun.spica.api.model.user.LoginResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

@OptIn(ExperimentalSerializationApi::class)
fun SpicaClient.User.login(
    name: String,
    password: String,
    remmember: Boolean = true,
): LoginResponse = runBlocking {
    val result = runCatching {
        userService.login(
            loginRequest = LoginRequest(
                name = name,
                password = password,
                remmember = remmember,
            ),
        )
    }
    if (result.isSuccess) {
        LoginResponse.Success
    } else {
        result.exceptionOrNull()
            ?.let {
                val message = it.message
                when {
                    it is HttpException -> it.decode()
                    !message.isNullOrBlank() -> LoginResponse.Failure(message = message)
                    else -> null
                }
            }
            ?: LoginResponse.Failure(message = "Unknown error.")
    }
}

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T> HttpException.decode(): T =
    Json.decodeFromString(response()?.errorBody()?.string().toString())
