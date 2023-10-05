package br.com.mdr.animeapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _enableLoading = MutableStateFlow(false)
    val enableLoading: StateFlow<Boolean> = _enableLoading

    protected fun launch(
        enableLoading: Boolean = false,
        errorBlock: ((Throwable) -> Unit?)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) =
        viewModelScope.launch {
            showLoading(enableLoading, true)
            runCatching {
                block()
            }.onSuccess {
                showLoading(enableLoading, false)
            }.onFailure {
                showLoading(enableLoading, false)
                errorBlock?.invoke(it)
            }
        }

    private fun showLoading(isLoadingEnabled: Boolean, showLoading: Boolean) {
        if (isLoadingEnabled) _enableLoading.value = showLoading
    }
}