@file:Suppress("unused")

package co.railgun.spica.api.function

import android.content.Context
import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.currentApplicationContext

fun SpicaApiClient.initialize(context: Context) {
    currentApplicationContext = context.applicationContext
}
