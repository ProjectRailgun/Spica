package co.railgun.common.api

import android.util.Log
import co.railgun.common.BuildConfig
import kotlinx.coroutines.runBlocking
import me.omico.cloudflare.api.dns.CloudflareDnsClient
import me.omico.cloudflare.api.dns.function.dnsQuery
import okhttp3.Dns
import java.io.IOException
import java.net.InetAddress
import java.net.InetAddress.getAllByName
import java.net.UnknownHostException

class HttpsDns : Dns {

    @Throws(UnknownHostException::class)
    override fun lookup(hostname: String): List<InetAddress> {
        if (hostname.contains("1slb.net")) {
            return Dns.SYSTEM.lookup(hostname)
        }

        try {
            val ips = runBlocking {
                CloudflareDnsClient.dnsQuery(hostname, "A")
                    .answer
                    .map { it.data }
            }
            if (ips.isEmpty()) {
                return Dns.SYSTEM.lookup(hostname)
            }

            return ips.flatMap { getAllByName(it).toList() }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) Log.e("DOH", e.message.toString())
        }

        return Dns.SYSTEM.lookup(hostname)
    }
}
