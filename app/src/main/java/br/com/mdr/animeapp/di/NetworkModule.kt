package br.com.mdr.animeapp.di

import br.com.mdr.animeapp.data.local.HeroDatabase
import br.com.mdr.animeapp.data.remote.HeroesApi
import br.com.mdr.animeapp.data.repository.RemoteDataSourceImpl
import br.com.mdr.animeapp.domain.repository.RemoteDataSource
import br.com.mdr.animeapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()


    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideHeroesApi(retrofit: Retrofit): HeroesApi =
        retrofit.create(HeroesApi::class.java)

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        heroesApi: HeroesApi,
        heroesDatabase: HeroDatabase
    ): RemoteDataSource  =
        RemoteDataSourceImpl(
            api = heroesApi,
            dataBase = heroesDatabase
        )

    @Provides
    @Singleton
    fun providesHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
}