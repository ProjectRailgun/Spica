package co.railgun.spica.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.railgun.spica.data.user.UserState
import co.railgun.spica.data.user.userRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _pendingActions: MutableSharedFlow<LoginAction> = MutableSharedFlow()

    val uiState: StateFlow<LoginUIState> = uiState()

    init {
        handlePendingActions()
    }

    fun submitAction(action: LoginAction) {
        viewModelScope.launch {
            _pendingActions.emit(action)
        }
    }

    private fun handlePendingActions() {
        viewModelScope.launch {
            _pendingActions.collect { action ->
                when (action) {
                    is LoginAction.Login -> login(action.username, action.password)
                    else -> return@collect
                }
            }
        }
    }

    private fun uiState(): StateFlow<LoginUIState> =
        userRepository.state.map { userState ->
            LoginUIState(
                logged = userState is UserState.Logged,
                error = when (userState) {
                    is UserState.LoginFailed -> LoginUIState.Error.Message(message = userState.message)
                    is UserState.Error ->
                        LoginUIState.Error.Message(
                            message = userState.exception.message ?: "Unknown error."
                        )
                    else -> LoginUIState.Error.None
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LoginUIState.Empty,
        )

    private suspend fun login(username: String, password: String) =
        userRepository.login(username, password)
}
