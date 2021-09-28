package co.railgun.spica.api.model.home

import co.railgun.spica.api.model.home.internal.Thumbnail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Episode(
    @SerialName("id") val id: String,
    @SerialName("bangumi_id") val bangumiId: String,
    @SerialName("bgm_eps_id") val bgmEpsId: Int,
    @SerialName("episode_no") val episodeNo: Int,
    @SerialName("name") val name: String,
    @SerialName("name_cn") val nameCn: String,
    @SerialName("duration") val duration: String,
    @SerialName("airdate") val airdate: String,
    @SerialName("status") val status: Int,
    @SerialName("create_time") val createTime: Long,
    @SerialName("update_time") val updateTime: Long,
    @SerialName("thumbnail") override val thumbnail: String,
    @SerialName("thumbnail_color") override val thumbnailColor: String,
    @SerialName("thumbnail_image") override val thumbnailImage: Image,
    @SerialName("bangumi") val bangumi: Bangumi,
) : Thumbnail
