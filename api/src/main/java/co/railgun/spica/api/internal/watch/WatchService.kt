package co.railgun.spica.api.internal.watch

import co.railgun.spica.api.model.watch.ChangeFavoriteBangumiStatusRequest
import co.railgun.spica.api.model.watch.SynchronizeHistoryRecordRequest
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
