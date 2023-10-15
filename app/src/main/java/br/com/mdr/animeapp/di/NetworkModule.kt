package br.com.mdr.animeapp.di

import br.com.mdr.animeapp.data.remote.HeroesApi
import br.com.mdr.animeapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.MINUTES)
            .connectTimeout(15, TimeUnit.MINUTES)
            .build()

    @Provides
    @Singleton
    fun provideHeroesApi(retrofit: Retrofit): HeroesApi =
        retrofit.create(HeroesApi::class.java)
}