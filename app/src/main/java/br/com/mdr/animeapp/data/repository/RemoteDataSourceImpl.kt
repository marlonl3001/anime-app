package br.com.mdr.animeapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.mdr.animeapp.data.local.HeroDatabase
import br.com.mdr.animeapp.data.pagingsource.HeroRemoteMediator
import br.com.mdr.animeapp.data.pagingsource.SearchHeroesSource
import br.com.mdr.animeapp.data.remote.HeroesApi
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.repository.RemoteDataSource
import br.com.mdr.animeapp.util.Constants.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val api: HeroesApi,
    private val dataBase: HeroDatabase
): RemoteDataSource {
    private val dao = dataBase.getHeroDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { dao.getHeroes() }
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            remoteMediator = HeroRemoteMediator(
                heroesApi = api,
                database = dataBase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                SearchHeroesSource(
                    api = api,
                    query = query
                )
            }
        ).flow
    }


}