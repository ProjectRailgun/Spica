@file:Suppress("unused")

package co.railgun.spica.api.function.watch.history

import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.internal.watchService
import co.railgun.spica.api.model.watch.SynchronizeHistoryRecordRequest

fun SpicaClient.Watch.History.synchronizeEpisodeHistory(
    recordRequestSynchronize: SynchronizeHistoryRecordRequest,
) {
    watchService.synchronizeWatchHistory(
        body = recordRequestSynchronize,
    )
}
