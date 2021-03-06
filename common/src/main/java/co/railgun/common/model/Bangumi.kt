package co.railgun.common.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

open class Bangumi(val id: String,
                   val name: String,
                   val name_cn: String,
                   val image: String,
                   val cover: String,
                   @SerializedName("cover_image")
                   val coverImage: CoverImage,
                   @SerializedName("cover_color")
                   val coverColor: String,
                   val summary: String,
                   val air_weekday: Int,
                   val air_date: String,
                   val eps: Int,
                   val type: Int,
                   val status: Int,
                   var favorite_status: Int,
                   val unwatched_count: Int,
                   val update_time: Long,
                   val bgm_id: Long) : BaseEntity() {
    enum class Status(val value: Int) { ALL(-1), WISH(1), WATCHED(2), WATCHING(3), PAUSE(4), ABANDONED(5) }
    enum class Type(val value: Int) { ALL(-1), SUB(1001), RAW(1002) }

    data class CoverImage(
        val value: Int,
        val dominant_color: String,
        val height: Int,
        val width: Int,
        val url: String

    )

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Bangumi) {
            return id == other.id
        }

        return false
    }

    fun isOnAir(): Boolean {
        val airDate = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).parse(air_date)
        val calendar = Calendar.getInstance(Locale.JAPAN)
        val rightNow = calendar.time
        calendar.time = airDate
        calendar.add(Calendar.WEEK_OF_YEAR, eps)
        return rightNow.before(calendar.time)
    }
}