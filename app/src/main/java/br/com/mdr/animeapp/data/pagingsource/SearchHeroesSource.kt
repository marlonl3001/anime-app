package br.com.mdr.animeapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.mdr.animeapp.data.remote.HeroesApi
import br.com.mdr.animeapp.domain.model.Hero
import javax.inject.Inject

class SearchHeroesSource @Inject constructor(
    private val api: HeroesApi,
    private val query: String
): PagingSource<Int, Hero>() {
    //Custom PagingSource class
    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        /**
         * This function takes a PagingState object as a parameter and returns the key to pass
         * into the load() method when the data is refreshed or invalidated after the initial load.
         * */

        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        /**
         * LoadParams - contains information about the load operation to be performed. This includes
         * the key to be loaded and the number of items to be loaded.
         * LoadResult - contains the result of the load operation. LoadResult is a sealed class
         * that takes one of two parameters:
         *  - If the load is successful, return a LoadResult.Page object
         *  - If the load is not successful, return a LoadResult.Error object
         * */

        return try {
            val apiResponse = api.searchHeroes(query)
            if (apiResponse.heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = apiResponse.heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = listOf(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}