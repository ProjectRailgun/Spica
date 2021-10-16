package co.railgun.spica.ui.bangumi.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.ProvideSpicaPreviewContainer
import co.railgun.spica.ui.bangumi.player.component.ExoPlayer
import co.railgun.spica.ui.bangumi.player.component.ExoPlayerState
import co.railgun.spica.ui.bangumi.player.component.rememberExoPlayerState
import co.railgun.spica.ui.component.NavigateUpIcon
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.component.SpicaTopAppBar
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.omico.xero.core.lifecycle.viewModel
import me.omico.xero.core.util.d

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
    BangumiPlayUI(
        navController = navController,
        uiState = uiState,
        onSubmitAction = viewModel::submitAction,
    )
}

private typealias OnSubmitBangumiDetailAction = OnSubmitAction<BangumiPlayerAction>

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BangumiPlayUI(
    navController: NavController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController(),
    exoPlayerState: ExoPlayerState = rememberExoPlayerState(),
    uiState: BangumiPlayerUIState,
    onSubmitAction: OnSubmitBangumiDetailAction,
) {
    exoPlayerState.isControllerVisible.d
    SideEffect {
        systemUiController.isSystemBarsVisible = exoPlayerState.isControllerVisible
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = exoPlayerState.isControllerVisible,
            ) {
                SpicaTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    titleText = uiState.title,
                    backgroundColor = Color.Transparent,
                    navigationIcon = { NavigateUpIcon(navController = navController) },
                    elevation = 0.dp,
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
