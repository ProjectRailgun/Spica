@file:Suppress("unused")

package co.railgun.spica.api.function.user

import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.internal.actionResponse
import co.railgun.spica.api.internal.userService
import co.railgun.spica.api.model.ActionResponse
import co.railgun.spica.api.model.user.LoginRequest

suspend fun SpicaClient.User.login(
    name: String,
    password: String,
    remember: Boolean = true,
): ActionResponse = actionResponse {
    userService.login(
        loginRequest = LoginRequest(
            name = name,
            password = password,
            remember = remember,
        ),
    )
}
