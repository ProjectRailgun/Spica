package co.railgun.spica.ui.bangumi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.railgun.spica.data.bangumi.Bangumi
import co.railgun.spica.data.bangumi.Detail
import co.railgun.spica.data.bangumi.bangumiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BangumiDetailViewModel(
    private val id: String,
) : ViewModel() {

    private val _pendingActions: MutableSharedFlow<BangumiDetailAction> = MutableSharedFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _bangumi: MutableSharedFlow<Bangumi> = MutableSharedFlow()

    private val _error: MutableStateFlow<BangumiDetailUIState.Error> =
        MutableStateFlow(BangumiDetailUIState.Error.None)

    val uiState: StateFlow<BangumiDetailUIState> = uiState()

    init {
        handlePendingActions()
        loadBangumiDetail()
    }

    fun submitAction(action: BangumiDetailAction) {
        viewModelScope.launch {
            _pendingActions.emit(action)
        }
    }

    private fun handlePendingActions() {
        viewModelScope.launch {
            _pendingActions.collect { action ->
                when (action) {
                    is BangumiDetailAction.Refresh -> refresh()
                    else -> return@collect
                }
            }
        }
    }

    private fun uiState(): StateFlow<BangumiDetailUIState> = combine(
        _loading,
        _bangumi,
        _error,
    ) {
        loading,
        bangumi,
        error,
        ->
        BangumiDetailUIState(
            loading = loading,
            title = bangumi.title,
            summary = bangumi.summary,
            episodes = bangumi.episodes,
            error = error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BangumiDetailUIState.Empty,
    )

    private fun loadBangumiDetail() {
        viewModelScope.launch { refresh() }
    }

    private suspend fun refresh() {
        _loading.emit(true)
        _error.emit(BangumiDetailUIState.Error.None)
        when (val bangumiDetail: Detail<Bangumi> = bangumiRepository.bangumiDetail(id)) {
            is Detail.Result -> _bangumi.emit(bangumiDetail.data)
            is Detail.Error -> _error.emit(
                BangumiDetailUIState.Error.Message(
                    message = bangumiDetail.exception.message ?: "Unknown error.",
                )
            )
            else -> _error.emit(BangumiDetailUIState.Error.Unauthorized)
        }
        _loading.emit(false)
    }
}
