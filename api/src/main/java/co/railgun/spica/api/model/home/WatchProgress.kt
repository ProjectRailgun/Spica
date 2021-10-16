package co.railgun.spica.api.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProgress(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("bangumi_id") val bangumiId: String,
    @SerialName("episode_id") val episodeId: String,
    @SerialName("watch_status") val watchStatus: Int,
    @SerialName("last_watch_position") val lastWatchPosition: Double,
    @SerialName("last_watch_time") val lastWatchTime: Long,
    @SerialName("percentage") val percentage: Double,
)
