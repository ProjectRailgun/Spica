package co.railgun.spica.api.internal

import retrofit2.Retrofit

internal val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(scalarsConverterFactory)
        .addConverterFactory(jsonConverterFactory)
        .build()
}
