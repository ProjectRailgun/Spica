package co.railgun.common.api

import co.railgun.common.cache.SpicaPreferences

/**
 * Created by roya on 2017/6/4.
 */

object ApiHelper {
    fun fixHttpUrl(url: String): String {
        if (url.startsWith("http")) {
            return url
        }

        return SpicaPreferences.getServer() + if (url.startsWith("/")) url.substring(1) else url
    }
}
