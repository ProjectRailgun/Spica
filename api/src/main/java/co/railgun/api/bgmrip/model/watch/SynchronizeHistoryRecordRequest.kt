package co.railgun.api.bgmrip.model.watch

import co.railgun.api.bgmrip.model.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SynchronizeHistoryRecordRequest(
    @SerialName("records") val records: List<HistoryRecord>,
) : Request
