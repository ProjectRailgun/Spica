package co.railgun.spica.ui.bangumi.detail

sealed class BangumiDetailAction {

    object Back : BangumiDetailAction()

    object NavigateToLogin : BangumiDetailAction()

    data class NavigateToBangumiPlayer(
        val id: String,
    ) : BangumiDetailAction()

    object Refresh : BangumiDetailAction()
}
