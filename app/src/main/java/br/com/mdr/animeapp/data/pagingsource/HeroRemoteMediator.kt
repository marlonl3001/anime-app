package br.com.mdr.animeapp.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.mdr.animeapp.data.local.HeroDatabase
import br.com.mdr.animeapp.data.remote.HeroesApi
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

private const val DEFAULT_PAGE_KEY = 1

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val heroesApi: HeroesApi,
    private val database: HeroDatabase
): RemoteMediator<Int, Hero>() {

    private val heroesDao = database.getHeroDao()
    private val heroesRemoteKeysDao = database.getRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state = state)
                    remoteKeys?.nextPage?.minus(DEFAULT_PAGE_KEY) ?: DEFAULT_PAGE_KEY
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state = state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state = state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = heroesApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                database.withTransaction {
                    //If MediatorResult is refreshing data, delete data from tables
                    if (loadType == LoadType.REFRESH) {
                        heroesDao.deleteAllHeroes()
                        heroesRemoteKeysDao.deleteAllRemoteKeys()
                    }

                    with(response) {
                        val keys = heroes.map { hero ->
                            HeroRemoteKeys(
                                id = hero.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }

                        heroesRemoteKeysDao.addAllRemoteKeys(keys)
                        heroesDao.insertHeroes(heroes = heroes)
                    }
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                heroesRemoteKeysDao.getRemoteKeys(it)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            heroesRemoteKeysDao.getRemoteKeys(it.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            heroesRemoteKeysDao.getRemoteKeys(it.id)
        }
    }
}