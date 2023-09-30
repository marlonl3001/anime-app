package br.com.mdr.animeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mdr.animeapp.data.local.dao.HeroDao
import br.com.mdr.animeapp.data.local.dao.HeroRemoteKeyDao
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.model.HeroRemoteKey

@Database(entities = [Hero::class, HeroRemoteKey::class], version = 1)
abstract class HeroDatabase: RoomDatabase() {
    abstract fun getHeroDao(): HeroDao
    abstract fun getRemoteKeyDao(): HeroRemoteKeyDao
}