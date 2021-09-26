@file:Suppress("unused")

package co.railgun.api.bgmrip.function.watch.history

import co.railgun.api.bgmrip.BgmRipClient
import co.railgun.api.bgmrip.internal.watchService
import co.railgun.api.bgmrip.model.watch.SynchronizeHistoryRecordRequest

fun BgmRipClient.Watch.History.synchronizeEpisodeHistory(
    recordRequestSynchronize: SynchronizeHistoryRecordRequest,
) {
    watchService.synchronizeWatchHistory(
        body = recordRequestSynchronize,
    )
}
