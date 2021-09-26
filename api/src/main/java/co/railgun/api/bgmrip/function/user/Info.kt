@file:Suppress("unused")

package co.railgun.api.bgmrip.function.user

import co.railgun.api.bgmrip.BgmRipClient
import co.railgun.api.bgmrip.internal.userService
import co.railgun.api.bgmrip.model.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun BgmRipClient.User.info(): UserInfo =
    withContext(Dispatchers.IO) { userService.info().data }
