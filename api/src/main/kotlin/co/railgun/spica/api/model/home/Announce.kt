package co.railgun.spica.api.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Announce(
    @SerialName("id") val id: String,
    @SerialName("content") val content: String,
    @SerialName("position") val position: Int,
    @SerialName("sort_order") val sortOrder: Int,
    @SerialName("start_time") val startTime: Long,
    @SerialName("end_time") val endTime: Long,
    @SerialName("bangumi") val bangumi: Bangumi,
)
