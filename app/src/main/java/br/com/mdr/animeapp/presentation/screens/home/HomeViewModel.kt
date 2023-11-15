package br.com.mdr.animeapp.presentation.screens.home

import br.com.mdr.animeapp.domain.usecase.HeroesUseCase
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HeroesUseCase
): BaseViewModel() {
    val heroes = useCase.getAllHeroes()

}