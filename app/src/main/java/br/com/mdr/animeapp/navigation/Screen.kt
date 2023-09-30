package br.com.mdr.animeapp.navigation

import br.com.mdr.animeapp.util.Constants.HERO_ID_KEY

sealed class Screen(val route: String) {
    data object Splash: Screen("splash_screen")
    data object Welcome: Screen("welcome_screen")
    data object Home: Screen("home_screen")
    data object Search: Screen("search_screen")
    data object Details: Screen("details_screen/{$HERO_ID_KEY}") {
        fun passHeroId(heroId: Int): String = "details_screen/$heroId"
    }
}
