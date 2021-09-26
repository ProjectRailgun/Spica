package co.railgun.api.bgmrip.internal

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal val client by lazy {
    OkHttpClient.Builder()
        .dns(BgmRipDns)
        .cookieJar(cookieJar)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}
