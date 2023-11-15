package br.com.mdr.animeapp.di

import android.content.Context
import br.com.mdr.animeapp.data.repository.DataStoreOperationsImpl
import br.com.mdr.animeapp.data.repository.HeroesRepositoryImpl
import br.com.mdr.animeapp.domain.repository.DataStoreOperations
import br.com.mdr.animeapp.domain.repository.HeroesRepository
import br.com.mdr.animeapp.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreOperations =
        DataStoreOperationsImpl(context)

    @Provides
    @Singleton
    fun providesHeroesRepository(dataSource: RemoteDataSource,
                                 dataStore: DataStoreOperations): HeroesRepository =
        HeroesRepositoryImpl(dataSource, dataStore)
}