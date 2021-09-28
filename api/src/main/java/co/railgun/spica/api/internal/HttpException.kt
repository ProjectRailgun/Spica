package co.railgun.spica.api.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T> HttpException.decodeErrorBody(): T =
    Json.decodeFromString(response()?.errorBody()?.string().toString())
