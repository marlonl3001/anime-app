package br.com.mdr.animeapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import br.com.mdr.animeapp.domain.repository.DataStoreOperations
import br.com.mdr.animeapp.util.Constants.DATA_STORE_NAME
import br.com.mdr.animeapp.util.Constants.ON_BOARDING_PREF_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
class DataStoreOperationsImpl(context: Context): DataStoreOperations {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = ON_BOARDING_PREF_KEY)
    }

    private val dataStore = context.dataStore
    override suspend fun saveOnboardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch {exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw  exception
                }
            }
            .map {preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }
}