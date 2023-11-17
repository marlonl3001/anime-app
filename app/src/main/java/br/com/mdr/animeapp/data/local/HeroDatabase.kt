package br.com.mdr.animeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mdr.animeapp.data.local.dao.HeroDao
import br.com.mdr.animeapp.data.local.dao.HeroRemoteKeysDao
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class HeroDatabase: RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): HeroDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, HeroDatabase::class.java)
            } else {
                Room.databaseBuilder(context, HeroDatabase::class.java, "test_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    abstract fun getHeroDao(): HeroDao
    abstract fun getRemoteKeysDao(): HeroRemoteKeysDao
}