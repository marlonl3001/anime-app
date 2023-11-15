package br.com.mdr.animeapp.domain.usecase

import androidx.paging.PagingData
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.repository.HeroesRepository
import kotlinx.coroutines.flow.Flow

class HeroesUseCase(
    private val repository: HeroesRepository
) {

    fun getAllHeroes(): Flow<PagingData<Hero>> = repository.getAllHeroes()

    fun readOnBoardingState(): Flow<Boolean> {
        return repository.readOnboardingState()
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        repository.saveOnboardingState(completed)
    }
}