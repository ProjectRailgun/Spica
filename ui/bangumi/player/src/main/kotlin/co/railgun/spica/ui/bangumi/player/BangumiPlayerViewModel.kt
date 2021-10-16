package co.railgun.spica.ui.bangumi.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.railgun.spica.data.bangumi.Episode
import co.railgun.spica.data.bangumi.bangumiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.omico.xero.core.util.d

class BangumiPlayerViewModel(
    private val id: String,
) : ViewModel() {

    private val _pendingActions: MutableSharedFlow<BangumiPlayerAction> = MutableSharedFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _episode: MutableSharedFlow<Episode> = MutableSharedFlow()

    private val _error: MutableStateFlow<BangumiPlayerUIState.Error> =
        MutableStateFlow(BangumiPlayerUIState.Error.None)

    val uiState: StateFlow<BangumiPlayerUIState> = uiState()

    init {
        handlePendingActions()
        loadEpisodeDetail()
    }

    fun submitAction(action: BangumiPlayerAction) {
        viewModelScope.launch {
            _pendingActions.emit(action)
        }
    }

    private fun handlePendingActions() {
        viewModelScope.launch {
            _pendingActions.collect { action ->
                when (action) {
                    else -> return@collect
                }
            }
        }
    }

    private fun uiState(): StateFlow<BangumiPlayerUIState> = combine(
        _loading,
        _episode,
        _error,
    ) {
        loading,
        episode,
        error,
        ->
        episode.d
        BangumiPlayerUIState(
            loading = loading,
            videoUrl = episode.videoUrl,
            error = error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BangumiPlayerUIState.Empty,
    )

    private fun loadEpisodeDetail() {
        viewModelScope.launch { refresh() }
    }

    private suspend fun refresh() {
        _loading.emit(true)
        _error.emit(BangumiPlayerUIState.Error.None)
        runCatching { _episode.emit(bangumiRepository.episodeDetail(id)) }
            .onFailure {
                _error.emit(
                    BangumiPlayerUIState.Error.Message(
                        message = it.message ?: "Unknown error.",
                    )
                )
            }
        _loading.emit(false)
    }
}
