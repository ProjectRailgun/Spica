package co.railgun.api.bgmrip.internal

import co.railgun.api.bgmrip.internal.home.HomeService
import co.railgun.api.bgmrip.internal.user.UserService
import co.railgun.api.bgmrip.internal.watch.WatchService
import retrofit2.create

internal val homeService: HomeService by lazyService()
internal val userService: UserService by lazyService()
internal val watchService: WatchService by lazyService()

private inline fun <reified T> lazyService() =
    lazy<T> { retrofit.create() }
