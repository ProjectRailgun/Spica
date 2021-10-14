package co.railgun.spica.ui.home

sealed class HomeAction {

    object Loading : HomeAction()

    object NavigateToBangumiDetail : HomeAction()
}
