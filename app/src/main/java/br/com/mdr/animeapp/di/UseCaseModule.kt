package br.com.mdr.animeapp.di

import br.com.mdr.animeapp.domain.repository.HeroesRepository
import br.com.mdr.animeapp.domain.usecase.HeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideHeroesUseCase(repository: HeroesRepository): HeroesUseCase {
        return HeroesUseCase(repository)
    }
}