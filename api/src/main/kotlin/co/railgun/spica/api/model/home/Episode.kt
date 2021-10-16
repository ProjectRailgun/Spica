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
    @SerialName("bangumi") val bangumi: Bangumi? = null,
    @SerialName("thumbnail") override val thumbnail: String,
    @SerialName("thumbnail_color") override val thumbnailColor: String? = null,
    @SerialName("thumbnail_image") override val thumbnailImage: Image? = null,
    @SerialName("watch_progress") val watchProgress: WatchProgress? = null,
    @SerialName("video_files") val videoFiles: List<VideoFile> = emptyList(),
) : Thumbnail {
    @Serializable
    data class VideoFile(
        @SerialName("id") val id: String,
        @SerialName("bangumi_id") val bangumiId: String,
        @SerialName("episode_id") val episodeId: String,
        @SerialName("file_name") val fileName: String? = null,
        @SerialName("file_path") val filePath: String,
        @SerialName("torrent_id") val torrentId: String,
        @SerialName("download_url") val downloadUrl: String,
        @SerialName("status") val status: Int,
        @SerialName("resolution_w") val resolutionW: Int,
        @SerialName("resolution_h") val resolutionH: Int,
        @SerialName("duration") val duration: Int,
        @SerialName("url") val url: String,
    )
}
