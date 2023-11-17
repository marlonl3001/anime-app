package br.com.mdr.animeapp.data.repository

import br.com.mdr.animeapp.data.local.HeroDatabase
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(database: HeroDatabase): LocalDataSource {

    private val heroDao = database.getHeroDao()
    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId)
    }
}