package me.omico.xero.core.lifecycle

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> viewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    crossinline factoryCreator: () -> VM,
): VM = viewModel(viewModelStoreOwner, key, viewModelFactory(factoryCreator))

inline fun <T : ViewModel> viewModelFactory(
    crossinline creator: () -> T,
) = object : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = creator() as T
}
