package co.railgun.common.model

import com.google.gson.annotations.SerializedName

/**
 * Created by roya on 2017/5/24.
 */

open class Announce(
    val id: String,
    val position: Int,

    val start_time: Long,
    val end_time: Long,
    @SerializedName("sort_order")
        val sortOrder: Long,
    val content: String,
    @SerializedName("image_url")
        val imageUrl: String,

    val bangumi: Bangumi): BaseEntity() {
        enum class Type(val value: Int) {NOTICE(1), RECOMMENDATION(2)}
}