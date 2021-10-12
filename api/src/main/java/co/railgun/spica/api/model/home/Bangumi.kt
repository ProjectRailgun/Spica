package co.railgun.spica.api.model.home

import co.railgun.spica.api.model.home.internal.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bangumi(
    @SerialName("id") val id: String,
    @SerialName("bgm_id") val bgmId: Int,
    @SerialName("name") val name: String,
    @SerialName("name_cn") val nameCn: String,
    @SerialName("type") val type: Int,
    @SerialName("eps") val eps: Int,
    @SerialName("summary") val summary: String,
    @SerialName("image") val image: String,
    @SerialName("air_date") val airDate: String,
    @SerialName("air_weekday") val airWeekday: Int,
    @SerialName("universal") override val universal: String = "",
    @SerialName("bangumi_moe") override val bangumiMoe: String = "",
    @SerialName("dmhy") override val dmhy: String = "",
    @SerialName("status") val status: Int,
    @SerialName("create_time") val createTime: Long,
    @SerialName("update_time") val updateTime: Long,
    @SerialName("cover_color") val coverColor: String,
    @SerialName("created_by_uid") val createdByUid: String,
    @SerialName("maintained_by_uid") val maintainedByUid: String,
    @SerialName("alert_timeout") val alertTimeout: Int,
    @SerialName("favorite_status") val favoriteStatus: Int = -1,
    @SerialName("favorite_update_time") val favoriteUpdateTime: Long = -1,
    @SerialName("favorite_check_time") val favoriteCheckTime: Long = -1,
    @SerialName("cover") val cover: String,
    @SerialName("cover_image") val coverImage: Image,
    @SerialName("eps_update_time") val epsUpdateTime: Long = -1,
    @SerialName("unwatched_count") val unwatchedCount: Int = -1,
    @SerialName("has_favorited_version") val hasFavoritedVersion: Boolean = false,
    @SerialName("episodes") val episodes: List<Episode> = emptyList(),
) : Source
