package co.railgun.spica.ui.bangumi.player.component

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.compose.runtime.Composable
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
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun ExoPlayer(
    modifier: Modifier = Modifier,
    state: ExoPlayerState = rememberExoPlayerState(),
    url: String,
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val exoPlayer = remember(context, lifecycle, url) { context.createExoPlayer(url) }
    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context)
                .apply {
                    setControllerVisibilityListener {
                        state.isControllerVisible = it == View.VISIBLE
                    }
                    state.restoreState(exoPlayer)
                    player = exoPlayer
                }
                .also { playerView ->
                    @Suppress("unused") val playerViewLifecycleObserver =
                        object : LifecycleObserver {

                            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                            fun onCreate() {
                                playerView.onResume()
                                exoPlayer.playWhenReady = state.autoPlay
                            }

                            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                            fun onPause() {
                                state.saveState(exoPlayer)
                                playerView.onPause()
                                exoPlayer.playWhenReady = false
                            }
                        }
                    lifecycle.addObserver(playerViewLifecycleObserver)
                }
        },
    )
}

private fun Context.createExoPlayer(url: String): ExoPlayer =
    SimpleExoPlayer.Builder(this).build().apply {
        ProgressiveMediaSource
            .Factory(defaultDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
            .let { addMediaSource(it) }
        prepare()
    }

private val Context.defaultDataSourceFactory
    get() = DefaultDataSourceFactory(this, exoplayerUserAgent)

private val Context.exoplayerUserAgent: String
    get() = Util.getUserAgent(this, packageName)
