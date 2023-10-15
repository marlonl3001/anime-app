package br.com.mdr.animeapp.di

import android.content.Context
import androidx.room.Room
import br.com.mdr.animeapp.data.local.HeroDatabase
import br.com.mdr.animeapp.util.Constants.HERO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Set the module and how it will be instantiated
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    //Provides a singleton database instance
    fun provideDatabase(@ApplicationContext context: Context): HeroDatabase =
        Room.databaseBuilder(
            context = context,
            klass = HeroDatabase::class.java,
            name = HERO_DATABASE
        ).build()
}