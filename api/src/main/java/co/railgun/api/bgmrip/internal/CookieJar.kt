package co.railgun.api.bgmrip.internal

import me.omico.cryonics.CryonicsCookieJar
import okhttp3.CookieJar

val cookieJar: CookieJar by lazy { CryonicsCookieJar(applicationContext) }
