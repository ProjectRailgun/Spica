package co.railgun.api.bgmrip.internal

import co.railgun.api.bgmrip.BgmRipService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

internal val service: BgmRipService by lazy {
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(scalarsConverterFactory)
        .addConverterFactory(jsonConverterFactory)
        .build()
        .create()
}

private val client by lazy {
    OkHttpClient.Builder()
        .dns(BgmRipDns)
        .cookieJar(cookieJar)
        .build()
}
