@file:Suppress("unused")

package co.railgun.spica.api.function.home

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.homeService
import co.railgun.spica.api.internal.listDataResponse

suspend fun SpicaApiClient.Home.myBangumi(
    page: Int = 1,
    count: Int = -1,
    status: BangumiStatus = BangumiStatus.DO,
) = listDataResponse {
    val queryMap = mapOf(
        "page" to page.toString(),
        "count" to count.toString(),
        "status" to status.value.toString()
    )
    homeService.myBangumi(queryMap)
}
