@file:Suppress("unused")

package co.railgun.spica.api.function.home

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.homeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun SpicaApiClient.Home.myBangumi(
    page: Int,
    count: Int,
    status: BangumiStatus,
) = withContext(Dispatchers.IO) {
    val queryMap = mapOf(
        "page" to page.toString(),
        "count" to count.toString(),
        "status" to status.value.toString()
    )
    homeService.myBangumi(queryMap)
}
