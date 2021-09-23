package co.railgun.api.bgmrip.internal

import android.util.Log
import co.railgun.api.bgmrip.BuildConfig
import kotlinx.coroutines.runBlocking
import me.omico.cloudflare.api.dns.CloudflareDnsClient
import me.omico.cloudflare.api.dns.function.dnsQuery
import okhttp3.Dns
import java.net.InetAddress

object BgmRipDns : Dns {

    override fun lookup(hostname: String): List<InetAddress> {
        if (!hostname.contains("bgm.rip")) return Dns.SYSTEM.lookup(hostname)
        runCatching {
            val ips = runBlocking {
                CloudflareDnsClient.dnsQuery(hostname, "A")
                    .answer
                    .map { it.data }
            }
            if (ips.isNotEmpty()) return ips.flatMap { InetAddress.getAllByName(it).toList() }
        }.onFailure {
            if (BuildConfig.DEBUG) Log.e("BgmRipDns: DOH", it.message.toString())
        }
        return Dns.SYSTEM.lookup(hostname)
    }
}
