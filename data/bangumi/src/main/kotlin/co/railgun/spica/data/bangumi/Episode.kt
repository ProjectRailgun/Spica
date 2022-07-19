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

    val canWatch: Boolean by lazy { downloadStatus == DownloadStatus.Downloaded }

    enum class DownloadStatus(val status: Int) {
        NotDownloaded(status = 0),
        Downloading(status = 1),
        Downloaded(status = 2),
    }
}
