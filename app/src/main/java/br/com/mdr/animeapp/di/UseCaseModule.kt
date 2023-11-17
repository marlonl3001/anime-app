package br.com.mdr.animeapp.di

import br.com.mdr.animeapp.domain.repository.HeroesRepository
import br.com.mdr.animeapp.domain.usecase.HeroDetailUseCase
import br.com.mdr.animeapp.domain.usecase.HeroesUseCase
import br.com.mdr.animeapp.domain.usecase.SearchHeroesUseCase
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

    @Provides
    @Singleton
    fun provideSearchHeroesUseCase(repository: HeroesRepository): SearchHeroesUseCase {
        return SearchHeroesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesHeroDetailUseCase(repository: HeroesRepository): HeroDetailUseCase {
        return HeroDetailUseCase(repository)
    }
}