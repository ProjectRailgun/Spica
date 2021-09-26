package co.railgun.api.bgmrip.model.watch

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryRecord(
    @SerialName("bangumi_id") val bangumiId: String,
    @SerialName("episode_id") val episodeId: String,
    @SerialName("last_watch_position") val lastWatchPosition: Double,
    @SerialName("last_watch_time") val lastWatchTime: Double,
    @SerialName("percentage") val percentage: Double,
    @SerialName("is_finished") val isFinished: Boolean,
)
