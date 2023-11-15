package br.com.mdr.animeapp.domain.usecase

import br.com.mdr.animeapp.domain.repository.HeroesRepository

class SearchHeroesUseCase(
    private val repository: HeroesRepository
) {

    fun searchHeroes(query: String) = repository.searchHeroes(query)
}