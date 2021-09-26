package co.railgun.api.bgmrip.internal

import retrofit2.Retrofit

internal val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(scalarsConverterFactory)
        .addConverterFactory(jsonConverterFactory)
        .build()
}
