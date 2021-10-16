package co.railgun.spica.api.internal

import android.content.Context

internal var currentApplicationContext: Context? = null

internal val applicationContext: Context
    get() = requireNotNull(currentApplicationContext) {
        "SpicaClient hasn't been initialized."
    }
