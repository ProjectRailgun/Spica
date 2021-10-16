package co.railgun.spica.data.bangumi

import co.railgun.spica.api.model.home.Announce as ApiAnnounce
import co.railgun.spica.api.model.home.Bangumi as ApiBangumi
import co.railgun.spica.api.model.home.Episode as ApiEpisode

fun ApiBangumi.toData(): Bangumi =
    Bangumi(
        id = id,
        title = nameCn,
        subTitle = name,
        bgmId = bgmId,
        coverUrl = cover,
        eps = eps,
        summary = summary,
        airDate = airDate,
        airWeekday = airWeekday,
        episodes = episodes.toEpisodes(),
    )

fun ApiEpisode.toData(): Episode =
    Episode(
        id = id,
        title = nameCn,
        subTitle = name,
        bangumiId = bangumiId,
        bgmEpsId = bgmEpsId,
        thumbnailUrl = thumbnailImage?.url,
        downloadStatus = status.toDownloadStatus(),
        videoUrl = videoFiles.firstOrNull()?.url ?: "",
    )

fun ApiAnnounce.toData(): Bangumi =
    bangumi.toData()

fun List<ApiBangumi>.toBangumi(): List<Bangumi> =
    map(ApiBangumi::toData)

fun List<ApiEpisode>.toEpisodes(): List<Episode> =
    map(ApiEpisode::toData)

fun List<ApiAnnounce>.toAnnouncedBangumi(): List<Bangumi> =
    map(ApiAnnounce::toData)

fun Int.toDownloadStatus(): Episode.DownloadStatus =
    Episode.DownloadStatus.values().find { it.status == this } ?: error("Status code incorrect.")
