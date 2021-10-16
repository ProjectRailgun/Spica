package co.railgun.spica.data.bangumi

data class Episode(
    val id: String,
    val title: String,
    val subTitle: String,
    val bangumiId: String,
    val bgmEpsId: Int,
    val thumbnailUrl: String?,
    val downloadStatus: DownloadStatus,
    val videoUrl: String,
) {
    enum class DownloadStatus(val status: Int) {
        NotDownload(status = 0),
        Downloading(status = 1),
        Downloaded(status = 2),
    }
}
