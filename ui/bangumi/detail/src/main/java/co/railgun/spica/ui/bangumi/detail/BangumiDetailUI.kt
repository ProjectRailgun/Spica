package co.railgun.spica.ui.bangumi.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.component.OnSubmitAction
import me.omico.xero.core.lifecycle.viewModel

@Composable
fun BangumiDetailUI(
    navController: NavController = rememberNavController(),
    id: String,
    viewModel: BangumiDetailViewModel = viewModel { BangumiDetailViewModel(id = id) },
) {
    BangumiDetailUI(
        onSubmitAction = viewModel::submitAction,
    )
}

private typealias OnSubmitBangumiDetailAction = OnSubmitAction<BangumiDetailAction>

@Composable
private fun BangumiDetailUI(
    onSubmitAction: OnSubmitBangumiDetailAction,
) {

}
