package co.railgun.spica.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.railgun.spica.data.user.UserState
import co.railgun.spica.data.user.userRepository
import co.railgun.spica.ui.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _route: MutableStateFlow<String> = MutableStateFlow(SplashScreen.route)
    val route: StateFlow<String> = _route.asStateFlow()

    init {
        checkUserState()
        collectUserState()
    }

    private fun checkUserState() {
        viewModelScope.launch {
            userRepository.updateUserState()
        }
    }

    private fun collectUserState() {
        viewModelScope.launch {
            userRepository.state.collect { userState ->
                if (userState is UserState.Initializing) return@collect
                val route = when (userState) {
                    is UserState.Logged -> Route.home
                    else -> Route.login
                }
                _route.emit(route)
            }
        }
    }
}
