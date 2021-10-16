package co.railgun.spica.api.internal

import co.railgun.spica.api.internal.home.HomeService
import co.railgun.spica.api.internal.user.UserService
import co.railgun.spica.api.internal.watch.WatchService
import retrofit2.create

internal val homeService: HomeService by lazyService()
internal val userService: UserService by lazyService()
internal val watchService: WatchService by lazyService()

private inline fun <reified T> lazyService() =
    lazy<T> { retrofit.create() }
