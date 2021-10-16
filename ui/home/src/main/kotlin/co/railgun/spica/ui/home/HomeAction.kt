package co.railgun.spica.ui.home

sealed class HomeAction {

    object Loading : HomeAction()

    data class NavigateToBangumiDetail(
        val id: String,
    ) : HomeAction()
}
