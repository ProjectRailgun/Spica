package co.railgun.spica.ui.bangumi.player

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.ProvideSpicaPreviewContainer
import co.railgun.spica.ui.bangumi.player.component.ExoPlayer
import co.railgun.spica.ui.bangumi.player.component.ExoPlayerState
import co.railgun.spica.ui.bangumi.player.component.rememberExoPlayerState
import co.railgun.spica.ui.component.NavigateUpIcon
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.theme.SpicaTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.omico.xero.core.lifecycle.viewModel

@Preview
@Composable
fun PreviewBangumiDetailUI() {
    ProvideSpicaPreviewContainer {
        BangumiPlayUI(
            uiState = BangumiPlayerUIState.Empty,
            onSubmitAction = {},
        )
    }
}

@Composable
fun BangumiPlayUI(
    navController: NavController = rememberNavController(),
    id: String,
    viewModel: BangumiPlayerViewModel = viewModel { BangumiPlayerViewModel(id = id) },
) {
    val uiState: BangumiPlayerUIState by viewModel.uiState.collectAsState()
    SpicaTheme(darkTheme = true) {
        BangumiPlayUI(
            uiState = uiState,
            onSubmitAction = { action ->
                when (action) {
                    BangumiPlayerAction.Back -> navController.navigateUp()
                }
            },
        )
    }
}

private typealias OnSubmitBangumiDetailAction = OnSubmitAction<BangumiPlayerAction>

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun BangumiPlayUI(
    systemUiController: SystemUiController = rememberSystemUiController(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    exoPlayerState: ExoPlayerState = rememberExoPlayerState(),
    uiState: BangumiPlayerUIState,
    onSubmitAction: OnSubmitBangumiDetailAction,
) {
    val onSubmitBackAction = { onSubmitAction(BangumiPlayerAction.Back) }
    BackHandler(onBack = onSubmitBackAction)
    DisposableEffect(Unit) {
        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = !darkTheme,
                isNavigationBarContrastEnforced = false,
            )
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = exoPlayerState.isControllerVisible,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                ViewCompat.getWindowInsetsController(LocalView.current)?.let {
                    when {
                        exoPlayerState.isControllerVisible -> it.show(WindowInsetsCompat.Type.systemBars())
                        else -> it.hide(WindowInsetsCompat.Type.systemBars())
                    }
                }
                SmallTopAppBar(
                    modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
                    title = { Text(text = uiState.title) },
                    navigationIcon = { NavigateUpIcon(onClick = onSubmitBackAction) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                )
            }
        },
    ) {
        Crossfade(targetState = uiState) {
            when {
                it.videoUrl.isNotBlank() ->
                    ExoPlayer(
                        modifier = Modifier.fillMaxSize(),
                        state = exoPlayerState,
                        url = it.videoUrl,
                    )
            }
        }
    }
}
