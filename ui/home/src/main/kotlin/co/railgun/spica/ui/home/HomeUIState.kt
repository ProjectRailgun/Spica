package co.railgun.spica.ui.home

import androidx.compose.runtime.Immutable
import co.railgun.spica.data.bangumi.Bangumi

@Immutable
data class HomeUIState(
    val announcedBangumi: List<Bangumi> = listOf(),
    val myBangumi: List<Bangumi> = listOf(),
    val onAir: List<Bangumi> = listOf(),
    val unauthorized: Boolean = false,
    val error: Throwable? = null,
) {
    companion object {
        val Empty: HomeUIState = HomeUIState()
    }
}
