package br.com.mdr.animeapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.usecase.SearchHeroesUseCase
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchHeroesUseCase
): BaseViewModel() {

    private val _heroes = MutableStateFlow<PagingData<Hero>>(PagingData.empty())
    val heroes = _heroes

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHeroes(query: String) {
        launch {
            useCase.searchHeroes(query).cachedIn(viewModelScope).collect {
                _heroes.value = it
            }
        }
    }
}