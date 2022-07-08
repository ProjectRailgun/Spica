package co.railgun.spica.ui.home

sealed class HomeAction {

    object Refresh : HomeAction()

    data class NavigateToBangumiDetail(
        val id: String,
    ) : HomeAction()
}
