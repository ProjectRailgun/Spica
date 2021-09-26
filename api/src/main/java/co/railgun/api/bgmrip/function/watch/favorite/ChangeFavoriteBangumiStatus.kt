@file:Suppress("unused")

package co.railgun.api.bgmrip.function.watch.favorite

import co.railgun.api.bgmrip.BgmRipClient
import co.railgun.api.bgmrip.function.home.BangumiStatus
import co.railgun.api.bgmrip.internal.watchService
import co.railgun.api.bgmrip.model.watch.ChangeFavoriteBangumiStatusRequest

fun BgmRipClient.Watch.Favorite.changeFavoriteBangumiStatus(
    bangumiId: String,
    status: BangumiStatus,
) {
    watchService.changeFavoriteBangumiStatus(
        bangumiId = bangumiId,
        body = ChangeFavoriteBangumiStatusRequest(status = status.value)
    )
}
