package co.bangumi.common.model

/**
 * Created by roya on 2017/5/24.
 */

open class Bangumi(val id: String,
                   val name: String,
                   val name_cn: String,
                   val image: String,
                   val cover: String,
                   val summary: String,
                   val air_weekday: Int,
                   val air_date: String,
                   val eps: Int,
                   val type: Int,
                   val status: Int,
                   var favorite_status: Int,
                   val unwatched_count: Int,
                   val update_time: Long,
                   val bgm_id: Long): BaseEntity() {
    enum class Status(val value: Int) { WISH(1), WATCHED(2), WATCHING(3), PAUSE(4), ABANDONED(5)}

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Bangumi) {
            return id == other.id
        }

        return false
    }
}