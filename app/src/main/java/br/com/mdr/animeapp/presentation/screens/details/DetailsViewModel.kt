package br.com.mdr.animeapp.presentation.screens.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.usecase.HeroDetailUseCase
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import br.com.mdr.animeapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: HeroDetailUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _hero: MutableStateFlow<Hero?> = MutableStateFlow(null)
    val hero: StateFlow<Hero?> = _hero

    init {
        launch(dispatcher = Dispatchers.IO) {
            val id = savedStateHandle.get<Int>(Constants.HERO_ID_KEY)
            _hero.value = id?.let {
                useCase.getSelectedHero(it)
            }
            _hero.value?.let {
                Log.d("HERO", "HERO: ${it.name} - ${it.anime}")
            }
        }
    }
}