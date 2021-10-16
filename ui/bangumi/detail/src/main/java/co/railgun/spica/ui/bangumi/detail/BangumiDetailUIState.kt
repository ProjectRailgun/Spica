package co.railgun.spica.ui.bangumi.detail

import androidx.compose.runtime.Immutable
import co.railgun.spica.data.bangumi.Episode

@Immutable
data class BangumiDetailUIState(
    val loading: Boolean = false,
    val title: String = "",
    val summary: String = "",
    val error: Error = Error.None,
    val episodes: List<Episode> = emptyList(),
) {

    sealed class Error {

        object None : Error()

        object Unauthorized : Error()

        data class Message(
            val message: String,
        ) : Error()
    }

    companion object {
        val Empty: BangumiDetailUIState = BangumiDetailUIState()
    }
}
