package co.railgun.spica.ui.navigation

import co.railgun.spica.ui.navigation.AppNavigationArgumentsScope.Companion.ARG_ID

object Route {
    const val splash = "splash"
    const val home = "home"
    const val login = "login"
    val bangumiDetail by lazy { "bangumi_detail/${ARG_ID.routeArgument}" }

    fun bangumiDetail(id: String): String =
        bangumiDetail.replace(ARG_ID.routeArgument, id)
}
