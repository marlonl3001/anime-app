package br.com.mdr.animeapp.data.remote

import br.com.mdr.animeapp.domain.model.Anime
import br.com.mdr.animeapp.domain.model.ApiResponse
import br.com.mdr.animeapp.domain.model.Hero

class MockHeroesApi: HeroesApi {

    private val heroes = listOf(
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

    override suspend fun getAllHeroes(page: Int): ApiResponse {
        return ApiResponse(success = false)
    }

    override suspend fun searchHeroes(name: String): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            heroes = findHeroes(name)
        )
    }

    private fun findHeroes(name: String?): List<Hero> {
        val heroesFounded = mutableListOf<Hero>()

        return if (!name.isNullOrEmpty()) {
            heroes.forEach { hero ->
                if(hero.name.lowercase().contains(name.lowercase())) {
                    heroesFounded.add(hero)
                }
            }
            heroesFounded
        } else {
            emptyList()
        }
    }
}