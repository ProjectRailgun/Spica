@file:Suppress("unused")

package co.railgun.spica.api.function.user

import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.internal.userService
import co.railgun.spica.api.model.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun SpicaClient.User.info(): UserInfo =
    withContext(Dispatchers.IO) { userService.info().data }
