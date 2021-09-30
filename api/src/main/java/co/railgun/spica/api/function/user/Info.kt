@file:Suppress("unused")

package co.railgun.spica.api.function.user

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.dataResponse
import co.railgun.spica.api.internal.userService
import co.railgun.spica.api.model.DataResponse
import co.railgun.spica.api.model.user.UserInfo

suspend fun SpicaApiClient.User.info(): DataResponse<UserInfo> =
    dataResponse { userService.info() }
