package co.railgun.spica.data.user

import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.function.user.info
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException

class UserRepository {

    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState.Initializing)
    val state: StateFlow<UserState> = _state.asStateFlow()

    suspend fun fetchUserInfo() {
        runCatching { SpicaClient.User.info() }
            .onFailure { exception ->
                _state.emit(
                    when {
                        exception is HttpException && exception.code() == 401 -> UserState.Unauthorized
                        else -> UserState.Error(exception)
                    }
                )
            }
            .onSuccess { userInfo ->
                _state.emit(userInfo.toUserState())
            }
    }
}

val userRepository: UserRepository by lazy { UserRepository() }
