package br.com.mdr.animeapp.data.repository

import androidx.paging.PagingData
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.repository.DataStoreOperations
import br.com.mdr.animeapp.domain.repository.HeroesRepository
import br.com.mdr.animeapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeroesRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource,
    private val dataStore: DataStoreOperations
): HeroesRepository {

    override fun getAllHeroes(): Flow<PagingData<Hero>> =
        dataSource.getAllHeroes()

    override suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.saveOnboardingState(completed)
    }

    override fun readOnboardingState(): Flow<Boolean> = dataStore.readOnBoardingState()

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> =
        dataSource.searchHeroes(query)
}