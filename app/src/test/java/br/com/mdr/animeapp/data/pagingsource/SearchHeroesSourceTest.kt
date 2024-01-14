package br.com.mdr.animeapp.data.pagingsource

import androidx.paging.PagingSource
import br.com.mdr.animeapp.data.remote.HeroesApi
import br.com.mdr.animeapp.data.remote.MockHeroesApi
import br.com.mdr.animeapp.domain.model.Anime
import br.com.mdr.animeapp.domain.model.Hero
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchHeroesSourceTest {

    private lateinit var heroesApi: HeroesApi
    private lateinit var heroes: List<Hero>

    @Before
    fun setup() {
        heroesApi = MockHeroesApi()
        heroes = listOf(
            Hero(
                id = 1,
                anime = Anime.ONE_PIECE.animeName,
                name = "Monkey D. Luffy",
                image = "/images/luffy.jpg",
                about = "Monkey D. Luffy, also known as \"Straw Hat Luffy\" and commonly as \"Straw Hat\",[10] is the founder and captain of the increasingly infamous and powerful Straw Hat Pirates, as well as the most powerful of its top fighters.[26][27] He desires to find the legendary treasure left behind by the late Gol D. Roger and thereby become the Pirate King,[28] which would help facilitate an unknown dream of his that he has told only to Shanks, his brothers, and crew.[29][30] He believes that being the Pirate King means having the most freedom in the world.[31]",
                rating = 5.0,
                power = 105,
                month = "May",
                day = "5th",
                family = listOf(
                    "Monkey D. Dragon",
                    "Monkey D. Garp",
                    "Portgas D. Ace",
                    "Curly Dadan",
                    "Sabo",
                    "Family Tree"
                ),
                abilities = listOf(
                    "Rifle",
                    "Ogon Rifle",
                    "Elephant Gun",
                    "Kane",
                    "Red Hawk"
                ),
                natureTypes = listOf(
                    "Rubber",
                    "Fire"
                )
            ),
            Hero(
                id = 2,
                anime = Anime.NARUTO.animeName,
                name = "Sasuke",
                image = "/images/sasuke.jpg",
                about = "Sasuke Uchiha (うちはサスケ, Uchiha Sasuke) is one of the last surviving members of Konohagakure's Uchiha clan. After his older brother, Itachi, slaughtered their clan, Sasuke made it his mission in life to avenge them by killing Itachi. He is added to Team 7 upon becoming a ninja and, through competition with his rival and best friend, Naruto Uzumaki.",
                rating = 5.0,
                power = 98,
                month = "July",
                day = "23rd",
                family = listOf(
                    "Fugaku",
                    "Mikoto",
                    "Itachi",
                    "Sarada",
                    "Sakura"
                ),
                abilities = listOf(
                    "Sharingan",
                    "Rinnegan",
                    "Sussano",
                    "Amateratsu",
                    "Intelligence"
                ),
                natureTypes = listOf(
                    "Lightning",
                    "Fire",
                    "Wind",
                    "Earth",
                    "Water"
                )
            ),
            Hero(
                id = 3,
                Anime.NARUTO.animeName,
                name = "Naruto",
                image = "/images/naruto.jpg",
                about = "Naruto Uzumaki (うずまきナルト, Uzumaki Naruto) is a shinobi of Konohagakure's Uzumaki clan. He became the jinchūriki of the Nine-Tails on the day of his birth — a fate that caused him to be shunned by most of Konoha throughout his childhood. After joining Team Kakashi, Naruto worked hard to gain the village's acknowledgement all the while chasing his dream to become Hokage.",
                rating = 5.0,
                power = 98,
                month = "Oct",
                day = "10th",
                family = listOf(
                    "Minato",
                    "Kushina",
                    "Boruto",
                    "Himawari",
                    "Hinata"
                ),
                abilities = listOf(
                    "Rasengan",
                    "Rasen-Shuriken",
                    "Shadow Clone",
                    "Senin Mode"
                ),
                natureTypes = listOf(
                    "Wind",
                    "Earth",
                    "Lava",
                    "Fire"
                )
            )
        )
    }

    @Test
    fun givenHeroesList_whenSearchingHeroesByName_thenReturnHeroesList() = runTest {
        val heroSource = SearchHeroesSource(heroesApi, "Luffy")
        assertEquals<PagingSource.LoadResult<Int, Hero>>(
            expected = PagingSource.LoadResult.Page(
                data = listOf(heroes.first()),
                prevKey = null,
                nextKey = null
            ),
            actual = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `Search api with existing hero name, expect multiple hero result, assert LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(api = heroesApi, query = "Sa")
            assertEquals<PagingSource.LoadResult<Int, Hero>>(
                expected = PagingSource.LoadResult.Page(
                    data = listOf(heroes[1]),
                    prevKey = null,
                    nextKey = null
                ),
                actual = heroSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with empty hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(api = heroesApi, query = "")
            val loadResult = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = heroesApi.searchHeroes("").heroes

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is PagingSource.LoadResult.Page }
        }

    @Test
    fun `Search api with non_existing hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val heroSource = SearchHeroesSource(api = heroesApi, query = "Unknown")
            val loadResult = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = heroesApi.searchHeroes("Unknown").heroes

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is PagingSource.LoadResult.Page }
        }
}