package co.bangumi.common.api

import okhttp3.Dns
import java.io.IOException
import java.net.InetAddress
import java.net.InetAddress.getAllByName
import java.net.UnknownHostException
import java.util.*

class HttpsDns : Dns {

    private val dnsService: HttpsDnsService

    init {
        dnsService = HttpsDnsService()
    }

    @Throws(UnknownHostException::class)
    override fun lookup(hostname: String): List<InetAddress> {
        if (hostname.contains("1slb.net"))
            return Dns.SYSTEM.lookup(hostname)

        try {
            val ips = dnsService.lookup(hostname)
            if (ips == null || ips.isEmpty()) {
                return Dns.SYSTEM.lookup(hostname)
            }

            val result = ArrayList<InetAddress>()
            for (ip in ips) {
                result.addAll(Arrays.asList(*getAllByName(ip)))
            }
            return result
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Dns.SYSTEM.lookup(hostname)
    }
}
