package co.railgun.api.bgmrip.internal

import android.content.Context

internal var currentApplicationContext: Context? = null

internal val applicationContext: Context
    get() = requireNotNull(currentApplicationContext) {
        "BgmRipClient hasn't been initialized."
    }
