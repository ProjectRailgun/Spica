package co.railgun.spica.ui.component

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestedOrientation(
    screenOrientation: Int,
) {
    val activity = LocalContext.current as Activity
    DisposableEffect(Unit) {
        activity.requestedOrientation = screenOrientation
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}
