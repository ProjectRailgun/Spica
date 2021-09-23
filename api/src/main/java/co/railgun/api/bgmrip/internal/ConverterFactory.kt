package co.railgun.api.bgmrip.internal

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.scalars.ScalarsConverterFactory

internal val scalarsConverterFactory by lazy { ScalarsConverterFactory.create() }

@OptIn(ExperimentalSerializationApi::class)
internal val jsonConverterFactory by lazy { Json.asConverterFactory("application/json".toMediaType()) }
