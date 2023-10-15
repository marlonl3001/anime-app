package br.com.mdr.animeapp.data.remote

import br.com.mdr.animeapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroesApi {

    @GET("/heroes")
    suspend fun getAllHeroes(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/heroes/search")
    suspend fun searchHeroes(
        @Query("name") name: String
    ): ApiResponse
}