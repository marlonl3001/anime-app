package br.com.mdr.animeapp.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mdr.animeapp.domain.usecase.HeroesUseCase
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCase: HeroesUseCase
): BaseViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        launch {
            useCase.saveOnBoardingState(completed)
        }
    }
}