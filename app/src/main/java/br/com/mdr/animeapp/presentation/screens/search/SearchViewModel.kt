package br.com.mdr.animeapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

): BaseViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}