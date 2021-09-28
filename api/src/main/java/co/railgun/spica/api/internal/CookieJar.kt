package co.railgun.spica.api.internal

import me.omico.cryonics.CryonicsCookieJar
import okhttp3.CookieJar

val cookieJar: CookieJar by lazy { CryonicsCookieJar(applicationContext) }
