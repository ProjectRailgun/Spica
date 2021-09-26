package co.railgun.api.bgmrip.internal

import okhttp3.OkHttpClient

internal val client by lazy {
    OkHttpClient.Builder()
        .dns(BgmRipDns)
        .cookieJar(cookieJar)
        .build()
}
