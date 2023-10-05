package br.com.mdr.animeapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnboardingState(completed: Boolean)
    //Not using suspend fun because when returning Flow is asynchronous by default
    fun readOnBoardingState(): Flow<Boolean>
}