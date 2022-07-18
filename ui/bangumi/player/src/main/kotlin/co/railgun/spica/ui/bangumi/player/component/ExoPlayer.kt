package co.railgun.spica.ui.bangumi.player.component

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

@Composable
fun ExoPlayer(
    modifier: Modifier = Modifier,
    state: ExoPlayerState = rememberExoPlayerState(),
    url: String,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val exoPlayer = remember { context.createExoPlayer(url) }
    val playerView = remember { StyledPlayerView(context) }

    @Suppress("unused")
    val playerViewLifecycleObserver = remember {
        object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                state.restoreState(exoPlayer)
                playerView.onResume()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                state.saveState(exoPlayer)
                playerView.onPause()
            }
        }
    }
    DisposableEffect(lifecycle, playerViewLifecycleObserver) {
        exoPlayer.prepare()
        lifecycle.addObserver(playerViewLifecycleObserver)
        onDispose {
            lifecycle.removeObserver(playerViewLifecycleObserver)
            exoPlayer.release()
        }
    }
    AndroidView(
        modifier = modifier,
        factory = {
            playerView.apply {
                setControllerVisibilityListener(
                    StyledPlayerView.ControllerVisibilityListener { visibility ->
                        state.isControllerVisible = visibility == View.VISIBLE
                    },
                )
                player = exoPlayer
            }
        },
    )
}

private fun Context.createExoPlayer(url: String): ExoPlayer =
    ExoPlayer.Builder(this).build().apply {
        ProgressiveMediaSource
            .Factory(defaultDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
            .let { addMediaSource(it) }
    }

private val Context.defaultDataSourceFactory
    get() = DefaultHttpDataSource.Factory().setUserAgent(exoplayerUserAgent)

private val Context.exoplayerUserAgent: String
    get() = Util.getUserAgent(this, packageName)
