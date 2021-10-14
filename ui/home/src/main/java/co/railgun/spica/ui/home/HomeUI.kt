package co.railgun.spica.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.ProvideSpicaPreviewContainer
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.component.SpicaTopAppBar
import co.railgun.spica.ui.component.liftOnScroll
import co.railgun.spica.ui.home.component.items
import com.google.accompanist.insets.ui.Scaffold

@Preview
@Composable
fun PreviewHomeUI() {
    ProvideSpicaPreviewContainer {
        HomeUI(
            uiState = HomeUIState.Empty,
            onSubmitAction = {}
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
        navController.popBackStack()
        navController.navigate("login")
    }
    HomeUI(
        uiState = uiState,
        onSubmitAction = { action ->
            when (action) {
                HomeAction.NavigateToBangumiDetail -> navController.navigate("bangumi_detail")
                else -> viewModel.submitAction(action)
            }
        },
    )
}

private typealias OnSubmitHomeAction = OnSubmitAction<HomeAction>

@Composable
private fun HomeUI(
    lazyListState: LazyListState = rememberLazyListState(),
    uiState: HomeUIState,
    onSubmitAction: OnSubmitHomeAction,
) {
    Scaffold(
        modifier = run {
            Modifier
                .fillMaxSize()
        },
        topBar = { SpicaTopAppBar(elevation = lazyListState.liftOnScroll()) },
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
    lazyListState: LazyListState = rememberLazyListState(),
    uiState: HomeUIState,
    onSubmitAction: OnSubmitHomeAction,
) {
    val announcedBangumi by rememberUpdatedState(newValue = uiState.announcedBangumi)
    val myBangumi by rememberUpdatedState(newValue = uiState.myBangumi)
    val onAir by rememberUpdatedState(newValue = uiState.onAir)
    val onItemClick = { onSubmitAction(HomeAction.NavigateToBangumiDetail) }
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        if (announcedBangumi.isNotEmpty()) {
            items(
                title = "Announce",
                items = announcedBangumi,
                onItemClick = onItemClick,
            )
        }
        if (myBangumi.isNotEmpty()) {
            items(
                title = "My Bangumi",
                items = myBangumi,
                onItemClick = onItemClick,
            )
        }
        if (onAir.isNotEmpty()) {
            items(
                title = "On Air",
                items = onAir,
                onItemClick = onItemClick,
            )
        }
    }
}
