@file:Suppress("unused")

package co.railgun.spica.api.function

import android.content.Context
import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.internal.currentApplicationContext

fun SpicaClient.initialize(context: Context) {
    currentApplicationContext = context.applicationContext
}
