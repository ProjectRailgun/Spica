package co.railgun.spica.data.bangumi

data class Bangumi(
    val id: String,
    val title: String,
    val subTitle: String,
    val bgmId: Int,
    val coverUrl: String,
    val eps: Int,
    val summary: String,
    val airDate: String,
    val airWeekday: Int,
    val episodes: List<Episode>,
)
