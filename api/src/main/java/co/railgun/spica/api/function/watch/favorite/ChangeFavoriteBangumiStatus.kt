@file:Suppress("unused")

package co.railgun.spica.api.function.watch.favorite

import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.function.home.BangumiStatus
import co.railgun.spica.api.internal.watchService
import co.railgun.spica.api.model.watch.ChangeFavoriteBangumiStatusRequest

fun SpicaClient.Watch.Favorite.changeFavoriteBangumiStatus(
    bangumiId: String,
    status: BangumiStatus,
) {
    watchService.changeFavoriteBangumiStatus(
        bangumiId = bangumiId,
        body = ChangeFavoriteBangumiStatusRequest(status = status.value)
    )
}
