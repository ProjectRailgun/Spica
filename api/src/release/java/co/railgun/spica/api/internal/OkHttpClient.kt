package co.railgun.spica.api.internal

import okhttp3.OkHttpClient

internal val client by lazy {
    OkHttpClient.Builder()
        .dns(SpicaDns)
        .cookieJar(cookieJar)
        .build()
}
