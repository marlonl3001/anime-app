package br.com.mdr.animeapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface HeroesRepository {

    suspend fun saveOnboardingState(completed: Boolean)
    fun readOnboardingState(): Flow<Boolean>
}