package br.com.mdr.animeapp.domain.repository

import br.com.mdr.animeapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}