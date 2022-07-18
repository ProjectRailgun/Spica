package co.railgun.spica.ui.bangumi.player.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun rememberExoPlayerState(
    autoPlay: Boolean = true,
    window: Int = 0,
    position: Long = 0L,
): ExoPlayerState = rememberSaveable(saver = ExoPlayerState.Saver) {
    ExoPlayerState(
        autoPlay = autoPlay,
        window = window,
        position = position,
    )
}

@Stable
class ExoPlayerState(
    autoPlay: Boolean = true,
    window: Int = 0,
    position: Long = 0L,
) {

    var autoPlay: Boolean by mutableStateOf(autoPlay)

    var window: Int by mutableStateOf(window)

    var position: Long by mutableStateOf(position)

    var isControllerVisible: Boolean by mutableStateOf(true)

    fun saveState(exoPlayer: ExoPlayer) {
        autoPlay = exoPlayer.playWhenReady
        window = exoPlayer.currentMediaItemIndex
        position = 0L.coerceAtLeast(exoPlayer.contentPosition)
    }

    fun restoreState(exoPlayer: ExoPlayer) {
        exoPlayer.playWhenReady = autoPlay
        exoPlayer.seekTo(window, position)
    }

    companion object {

        private const val autoPlay = "autoPlay"
        private const val window = "window"
        private const val position = "position"

        val Saver: Saver<ExoPlayerState, Any> = mapSaver(
            save = {
                mapOf(
                    autoPlay to it.autoPlay,
                    window to it.window,
                    position to it.position,
                )
            },
            restore = {
                ExoPlayerState(
                    autoPlay = it[autoPlay] as Boolean,
                    window = it[window] as Int,
                    position = it[position] as Long,
                )
            },
        )
    }
}
