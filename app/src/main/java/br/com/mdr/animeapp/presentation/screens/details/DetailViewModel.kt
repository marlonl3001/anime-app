package br.com.mdr.animeapp.presentation.screens.details

import androidx.compose.runtime.mutableStateOf
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.usecase.HeroDetailUseCase
import br.com.mdr.animeapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: HeroDetailUseCase
): BaseViewModel() {
    private val _hero = mutableStateOf<Hero>()
}