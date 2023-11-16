package br.com.mdr.animeapp.domain.usecase

import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.repository.HeroesRepository

class HeroDetailUseCase(private val repository: HeroesRepository) {

    suspend fun getSelectedHero(heroId: Int): Hero = repository.getHero(heroId)
}