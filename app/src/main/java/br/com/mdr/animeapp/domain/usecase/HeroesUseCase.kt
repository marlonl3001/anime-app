package br.com.mdr.animeapp.domain.usecase

import br.com.mdr.animeapp.domain.repository.HeroesRepository
import kotlinx.coroutines.flow.Flow

class HeroesUseCase(
    private val repository: HeroesRepository
) {

    fun readOnBoardingState(): Flow<Boolean> {
        return repository.readOnboardingState()
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        repository.saveOnboardingState(completed)
    }
}