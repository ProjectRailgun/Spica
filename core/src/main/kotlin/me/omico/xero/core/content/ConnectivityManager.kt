@file:Suppress("unused")

package me.omico.xero.core.content

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission

val Context.connectivityManager: ConnectivityManager
    get() = requireSystemService()

val Context.localIpAddress: String
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() = connectivityManager.localIpAddress

val ConnectivityManager.localIpAddress: String
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() {
        getLinkProperties(activeNetwork)!!.linkAddresses.forEach {
            val hostAddress = it.address.hostAddress
            if (hostAddress != null && hostAddress.startsWith("192.168.")) return hostAddress
        }
        return ""
    }
