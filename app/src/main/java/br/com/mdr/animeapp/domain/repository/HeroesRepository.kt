package br.com.mdr.animeapp.domain.repository

import androidx.paging.PagingData
import br.com.mdr.animeapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface HeroesRepository {

    suspend fun saveOnboardingState(completed: Boolean)
    fun readOnboardingState(): Flow<Boolean>

    fun getAllHeroes(): Flow<PagingData<Hero>>

    fun searchHeroes(query: String): Flow<PagingData<Hero>>

    suspend fun getHero(heroId: Int): Hero
}