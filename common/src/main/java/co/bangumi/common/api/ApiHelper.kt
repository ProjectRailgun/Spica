package co.bangumi.common.api

import co.bangumi.common.cache.CygnusPreferences

/**
 * Created by roya on 2017/6/4.
 */

object ApiHelper {
    fun fixHttpUrl(url: String): String {
        if (url.startsWith("http")) {
            return url
        }

        return CygnusPreferences.getServer() + if (url.startsWith("/")) url.substring(1) else url
    }
}
