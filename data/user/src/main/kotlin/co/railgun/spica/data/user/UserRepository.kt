package co.railgun.spica.data.user

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.function.user.info
import co.railgun.spica.api.function.user.login
import co.railgun.spica.api.model.ActionResponse
import co.railgun.spica.api.model.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepository {

    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState.Initializing)
    val state: StateFlow<UserState> = _state.asStateFlow()

    suspend fun updateUserState() {
        val userState = when (val response = SpicaApiClient.User.info()) {
            is DataResponse.Ok -> response.data.toUserState()
            is DataResponse.Unauthorized -> UserState.Unauthorized
            is DataResponse.Error -> UserState.Error(response.exception)
        }
        _state.emit(userState)
    }

    suspend fun login(username: String, password: String) {
        when (val response = SpicaApiClient.User.login(username, password)) {
            is ActionResponse.Ok -> updateUserState()
            is ActionResponse.Message -> _state.emit(UserState.LoginFailed(response.message))
        }
    }
}

val userRepository: UserRepository by lazy { UserRepository() }