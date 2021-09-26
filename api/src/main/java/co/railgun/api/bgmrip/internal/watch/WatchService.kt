package co.railgun.api.bgmrip.internal.watch

import co.railgun.api.bgmrip.model.watch.ChangeFavoriteBangumiStatusRequest
import co.railgun.api.bgmrip.model.watch.SynchronizeHistoryRecordRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface WatchService {

    @POST("/api/watch/favorite/bangumi/{bangumi_id}")
    fun changeFavoriteBangumiStatus(
        @Path("bangumi_id") bangumiId: String,
        @Body body: ChangeFavoriteBangumiStatusRequest,
    )

    @POST("/api/watch/history/synchronize")
    fun synchronizeWatchHistory(
        @Body body: SynchronizeHistoryRecordRequest,
    )
}
