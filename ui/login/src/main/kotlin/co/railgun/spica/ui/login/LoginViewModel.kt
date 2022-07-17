package co.railgun.spica.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.railgun.spica.data.user.UserState
import co.railgun.spica.data.user.userRepository
import co.railgun.spica.ui.UIError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _pendingActions: MutableSharedFlow<LoginAction> = MutableSharedFlow()

    private val _loggingIn: MutableStateFlow<Boolean> = MutableStateFlow(false)

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
        combine(
            _loggingIn,
            userRepository.state,
        ) {
                loggingIn,
                userState,
            ->
            LoginUIState(
                logged = userState is UserState.Logged,
                loginError = when {
                    loggingIn -> null
                    userState is UserState.LoginFailed -> UIError(userState.message)
                    userState is UserState.Error -> UIError(userState.exception)
                    else -> null
                },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LoginUIState.Empty,
        )

    private suspend fun login(username: String, password: String) {
        _loggingIn.emit(true)
        userRepository.login(username, password)
        _loggingIn.emit(false)
    }
}
