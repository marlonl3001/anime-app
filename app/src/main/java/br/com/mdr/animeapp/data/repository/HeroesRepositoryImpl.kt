package br.com.mdr.animeapp.data.repository

import br.com.mdr.animeapp.domain.repository.DataStoreOperations
import br.com.mdr.animeapp.domain.repository.HeroesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeroesRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreOperations
): HeroesRepository {
    override suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.saveOnboardingState(completed)
    }

    override fun readOnboardingState(): Flow<Boolean> = dataStore.readOnBoardingState()
}