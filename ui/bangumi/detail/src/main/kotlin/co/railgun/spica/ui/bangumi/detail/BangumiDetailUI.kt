package co.railgun.spica.ui.bangumi.detail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.data.bangumi.Episode.DownloadStatus
import co.railgun.spica.ui.ProvideSpicaPreviewContainer
import co.railgun.spica.ui.bangumi.detail.component.Episode
import co.railgun.spica.ui.bangumi.detail.component.Summary
import co.railgun.spica.ui.component.HeightSpacer
import co.railgun.spica.ui.component.ItemTitle
import co.railgun.spica.ui.component.LocalLazyListState
import co.railgun.spica.ui.component.NavigateUpIcon
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.component.ProvideLazyListState
import co.railgun.spica.ui.component.SpicaTopAppBar
import co.railgun.spica.ui.component.liftOnScroll
import co.railgun.spica.ui.navigation.navigateToBangumiPlayer
import co.railgun.spica.ui.navigation.navigateToLogin
import com.google.accompanist.insets.ui.Scaffold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.omico.xero.core.lifecycle.viewModel

@Preview
@Composable
fun PreviewBangumiDetailUI() {
    ProvideSpicaPreviewContainer {
        BangumiDetailUI(
            uiState = BangumiDetailUIState.Empty.copy(
                title = "Title",
            ),
            onSubmitAction = {},
        )
    }
}

@Composable
fun BangumiDetailUI(
    navController: NavController = rememberNavController(),
    id: String,
    viewModel: BangumiDetailViewModel = viewModel { BangumiDetailViewModel(id = id) },
) {
    val uiState: BangumiDetailUIState by viewModel.uiState.collectAsState()
    ProvideLazyListState {
        BangumiDetailUI(
            uiState = uiState,
            onSubmitAction = { action ->
                when (action) {
                    is BangumiDetailAction.Back -> navController.navigateUp()
                    is BangumiDetailAction.NavigateToLogin -> navController.navigateToLogin()
                    is BangumiDetailAction.NavigateToBangumiPlayer ->
                        navController.navigateToBangumiPlayer(action.id)
                    else -> viewModel.submitAction(action)
                }
            },
        )
    }
}

private typealias OnSubmitBangumiDetailAction = OnSubmitAction<BangumiDetailAction>

@Composable
private fun BangumiDetailUI(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    uiState: BangumiDetailUIState,
    onSubmitAction: OnSubmitBangumiDetailAction,
) {
    val onSubmitBackAction = { onSubmitAction(BangumiDetailAction.Back) }
    BackHandler(onBack = onSubmitBackAction)
    LaunchedEffect(uiState) {
        if (uiState.error !is BangumiDetailUIState.Error.Message) return@LaunchedEffect
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(uiState.error.message)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            SpicaTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                titleText = uiState.title,
                navigationIcon = { NavigateUpIcon(onClick = onSubmitBackAction) },
                elevation = LocalLazyListState.current.liftOnScroll(),
            )
        },
    ) { innerPadding ->
        BangumiDetailContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onSubmitAction = onSubmitAction,
        )
    }
}

@Composable
private fun BangumiDetailContent(
    modifier: Modifier = Modifier,
    uiState: BangumiDetailUIState,
    onSubmitAction: OnSubmitBangumiDetailAction,
) {
    Crossfade(
        modifier = modifier,
        targetState = uiState.error,
    ) { error ->
        when (error) {
            is BangumiDetailUIState.Error.None -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = LocalLazyListState.current,
                ) {
                    if (uiState.summary.isNotBlank()) {
                        item { ItemTitle(text = "Summary") }
                        item {
                            Summary(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                                text = uiState.summary,
                            )
                        }
                        item { HeightSpacer(height = 16.dp) }
                    }
                    if (uiState.episodes.count() > 0) {
                        item { ItemTitle(text = "Episodes") }
                        itemsIndexed(uiState.episodes) { index, episode ->
                            Episode(
                                number = index + 1,
                                episode = episode,
                                onclick = {
                                    if (episode.downloadStatus == DownloadStatus.Downloaded) {
                                        onSubmitAction(
                                            BangumiDetailAction.NavigateToBangumiPlayer(episode.id)
                                        )
                                    }
                                },
                            )
                        }
                        item { HeightSpacer(height = 16.dp) }
                    }
                }
            }
            is BangumiDetailUIState.Error.Unauthorized -> onSubmitAction(BangumiDetailAction.NavigateToLogin)
            else -> return@Crossfade
        }
    }
}
