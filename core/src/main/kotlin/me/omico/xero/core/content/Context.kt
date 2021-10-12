package me.omico.xero.core.content

import android.content.Context
import androidx.core.content.getSystemService

inline fun <reified T : Any> Context.requireSystemService(): T =
    requireNotNull(getSystemService())
