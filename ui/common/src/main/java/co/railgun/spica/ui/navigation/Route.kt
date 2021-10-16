package co.railgun.spica.ui.navigation

import co.railgun.spica.ui.navigation.AppNavigationArgumentsScope.Companion.ARG_BANGUMI_ID
import co.railgun.spica.ui.navigation.AppNavigationArgumentsScope.Companion.ARG_EPISODE_ID

object Route {

    const val splash = "splash"

    const val home = "home"

    const val login = "login"

    val bangumiDetail by lazy { "bangumi_detail/${ARG_BANGUMI_ID.routeArgument}" }
    fun bangumiDetail(id: String): String =
        bangumiDetail.replace(ARG_BANGUMI_ID.routeArgument, id)

    val bangumiPlayer by lazy { "bangumi_player/${ARG_EPISODE_ID.routeArgument}" }
    fun bangumiPlayer(id: String): String =
        bangumiPlayer.replace(ARG_EPISODE_ID.routeArgument, id)
}
