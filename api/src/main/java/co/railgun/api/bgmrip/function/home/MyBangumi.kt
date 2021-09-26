@file:Suppress("unused")

package co.railgun.api.bgmrip.function.home

import co.railgun.api.bgmrip.BgmRipClient
import co.railgun.api.bgmrip.internal.homeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun BgmRipClient.Home.myBangumi(
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
