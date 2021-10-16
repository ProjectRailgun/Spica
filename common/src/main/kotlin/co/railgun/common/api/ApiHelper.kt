package co.railgun.common.api

/**
 * Created by roya on 2017/6/4.
 */

object ApiHelper {
    fun fixHttpUrl(url: String): String {
        if (url.startsWith("http")) {
            return url
        }

        return "https://bgm.rip/" + if (url.startsWith("/")) url.substring(1) else url
    }
}
