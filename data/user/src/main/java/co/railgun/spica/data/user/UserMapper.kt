package co.railgun.spica.data.user

import co.railgun.spica.api.model.user.UserInfo

fun UserInfo.toUserState(): UserState.Logged =
    UserState.Logged(
        id = id,
        name = name,
        level = level,
        email = email,
        emailConfirmed = emailConfirmed,
    )
