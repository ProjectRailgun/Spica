package co.railgun.spica.ui.bangumi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BangumiDetailViewModel(id: String) : ViewModel() {

    private val _pendingActions: MutableSharedFlow<BangumiDetailAction> = MutableSharedFlow()

    init {
        handlePendingActions()
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
                    else -> return@collect
                }
            }
        }
    }
}
