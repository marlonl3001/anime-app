package br.com.mdr.animeapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _enableLoading = MutableStateFlow(false)
    val enableLoading: StateFlow<Boolean> = _enableLoading

    protected fun launch(
        enableLoading: Boolean = false,
        dispatcher: CoroutineDispatcher? = null,
        errorBlock: ((Throwable) -> Unit?)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = dispatcher?.let {
        viewModelScope.launch(dispatcher) {
            processLaunchBlock(
                enableLoading,
                this,
                block,
                errorBlock
            )
        }
    } ?:
        viewModelScope.launch {
            processLaunchBlock(
                enableLoading,
                this,
                block,
                errorBlock
            )
        }

    private suspend fun processLaunchBlock(
        enableLoading: Boolean,
        scope: CoroutineScope,
        block: suspend CoroutineScope.() -> Unit,
        errorBlock: ((Throwable) -> Unit?)? = null
    ) {

        showLoading(enableLoading, true)

        scope.apply {
            runCatching {
                block()
            }.onSuccess {
                showLoading(enableLoading, false)
            }.onFailure {
                showLoading(enableLoading, false)
                errorBlock?.invoke(it)
            }
        }
    }

    private fun showLoading(isLoadingEnabled: Boolean, showLoading: Boolean) {
        if (isLoadingEnabled) _enableLoading.value = showLoading
    }
}