package br.com.mdr.animeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mdr.animeapp.data.local.dao.HeroDao
import br.com.mdr.animeapp.data.local.dao.HeroRemoteKeysDao
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class HeroDatabase: RoomDatabase() {
    abstract fun getHeroDao(): HeroDao
    abstract fun getRemoteKeysDao(): HeroRemoteKeysDao
}