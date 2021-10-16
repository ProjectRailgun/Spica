package co.railgun.spica.ui.bangumi.player

import androidx.compose.runtime.Immutable

@Immutable
data class BangumiPlayerUIState(
    val loading: Boolean = false,
    val title: String = "",
    val videoUrl: String = "",
    val error: Error = Error.None,
) {

    sealed class Error {

        object None : Error()

        object Unauthorized : Error()

        data class Message(
            val message: String,
        ) : Error()
    }

    companion object {
        val Empty: BangumiPlayerUIState = BangumiPlayerUIState()
    }
}
