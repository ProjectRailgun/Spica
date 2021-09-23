@file:Suppress("unused")

package co.railgun.api.bgmrip.function

import android.content.Context
import co.railgun.api.bgmrip.BgmRipClient
import co.railgun.api.bgmrip.internal.currentApplicationContext

fun BgmRipClient.initialize(context: Context) {
    currentApplicationContext = context.applicationContext
}
