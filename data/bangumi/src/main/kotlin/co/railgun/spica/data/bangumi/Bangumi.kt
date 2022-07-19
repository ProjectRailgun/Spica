package co.railgun.spica.data.bangumi

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

data class Bangumi(
    val id: String,
    val title: String,
    val subTitle: String,
    val bgmId: Int,
    val coverColor: String,
    val coverUrl: String,
    val eps: Int,
    val summary: String,
    val airDate: String,
    val airWeekday: Int,
    val episodes: List<Episode>,
) {
    val placeholder: Drawable by lazy { ColorDrawable(Color.parseColor(coverColor)) }
}
