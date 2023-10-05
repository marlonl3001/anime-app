package br.com.mdr.animeapp.presentation.screens.splash

import br.com.mdr.animeapp.domain.usecase.HeroesUseCase
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: HeroesUseCase
): BaseViewModel() {

    private val _onBoardingStatus = MutableStateFlow(false)
    val onboardingStatus: StateFlow<Boolean> = _onBoardingStatus

    init {
        launch {
            _onBoardingStatus.value = useCase.readOnBoardingState().stateIn(this).value
        }
    }
}