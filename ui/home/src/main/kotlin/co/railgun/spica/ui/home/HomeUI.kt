package co.railgun.spica.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.data.bangumi.Bangumi
import co.railgun.spica.ui.ProvideSpicaPreviewContainer
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.component.SpicaTopAppBar
import co.railgun.spica.ui.home.component.items
import co.railgun.spica.ui.navigation.navigateToBangumiDetail
import co.railgun.spica.ui.navigation.navigateToLogin

@Preview
@Composable
fun PreviewHomeUI() {
    ProvideSpicaPreviewContainer {
        HomeUI(
            uiState = HomeUIState.Empty,
            onSubmitAction = {},
        )
    }
}

@Composable
fun HomeUI(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState: HomeUIState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState) {
        if (!uiState.unauthorized) return@LaunchedEffect
        navController.navigateToLogin()
    }
    HomeUI(
        uiState = uiState,
        onSubmitAction = { action ->
            when (action) {
                is HomeAction.NavigateToBangumiDetail ->
                    navController.navigateToBangumiDetail(action.id)
                else -> viewModel.submitAction(action)
            }
        },
    )
}

private typealias OnSubmitHomeAction = OnSubmitAction<HomeAction>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeUI(
    lazyListState: LazyListState = rememberLazyListState(),
    uiState: HomeUIState,
    onSubmitAction: OnSubmitHomeAction,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SpicaTopAppBar() },
    ) { innerPadding ->
        HomeContent(
            modifier = run {
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            },
            lazyListState = lazyListState,
            uiState = uiState,
            onSubmitAction = onSubmitAction,
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    uiState: HomeUIState,
    onSubmitAction: OnSubmitHomeAction,
) {
    Crossfade(targetState = uiState) { state ->
        when {
            state.isLoading ->
                Loading(modifier = modifier)
            state.isDataEmpty ->
                EmptyHomeContent(
                    modifier = modifier,
                    onRefreshClick = { onSubmitAction(HomeAction.Refresh) },
                )
            else ->
                BangumiList(
                    modifier = modifier,
                    lazyListState = lazyListState,
                    uiState = state,
                    onItemClick = { onSubmitAction(HomeAction.NavigateToBangumiDetail(id)) },
                )
        }
    }
}

@Composable
fun Loading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyHomeContent(
    modifier: Modifier = Modifier,
    onRefreshClick: () -> Unit,
) {
    Box(
        modifier = modifier.clickable(onClick = onRefreshClick),
        contentAlignment = Alignment.Center,
    ) {
        Text("No data")
    }
}

@Composable
fun BangumiList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    uiState: HomeUIState,
    onItemClick: Bangumi.() -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        if (uiState.announcedBangumi.isNotEmpty()) {
            items(
                title = "Announce",
                items = uiState.announcedBangumi,
                onItemClick = onItemClick,
            )
        }
        if (uiState.myBangumi.isNotEmpty()) {
            items(
                title = "My Bangumi",
                items = uiState.myBangumi,
                onItemClick = onItemClick,
            )
        }
        if (uiState.onAir.isNotEmpty()) {
            items(
                title = "On Air",
                items = uiState.onAir,
                onItemClick = onItemClick,
            )
        }
    }
}
