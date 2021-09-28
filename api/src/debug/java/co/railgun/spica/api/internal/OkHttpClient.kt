package co.railgun.spica.api.internal

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal val client by lazy {
    OkHttpClient.Builder()
        .dns(SpicaDns)
        .cookieJar(cookieJar)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}
