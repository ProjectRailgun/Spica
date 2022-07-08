package co.railgun.spica.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.railgun.spica.data.bangumi.UpdateState
import co.railgun.spica.data.bangumi.bangumiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _pendingActions: MutableSharedFlow<HomeAction> = MutableSharedFlow()

    val uiState: StateFlow<HomeUIState> = uiState()

    init {
        handlePendingActions()
        initialize()
    }

    fun submitAction(action: HomeAction) {
        viewModelScope.launch {
            _pendingActions.emit(action)
        }
    }

    private fun handlePendingActions() {
        viewModelScope.launch {
            _pendingActions.collect { action ->
                when (action) {
                    HomeAction.Refresh -> refresh()
                    else -> return@collect
                }
            }
        }
    }

    private fun uiState(): StateFlow<HomeUIState> = combine(
        bangumiRepository.announcedBangumi,
        bangumiRepository.myBangumi,
        bangumiRepository.onAir,
        bangumiRepository.updateState,
    ) {
        announcedBangumi,
        myBangumi,
        onAir,
        updateState,
        ->
        HomeUIState(
            announcedBangumi = announcedBangumi,
            myBangumi = myBangumi,
            onAir = onAir,
            unauthorized = updateState is UpdateState.Unauthorized,
            error = when (updateState) {
                is UpdateState.Error -> updateState.exception
                else -> null
            },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUIState.Empty,
    )

    private fun initialize() {
        viewModelScope.launch {
            refresh()
        }
    }

    private suspend fun refresh() =
        bangumiRepository.updateBangumiState()
}
