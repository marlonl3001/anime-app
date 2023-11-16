package br.com.mdr.animeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.mdr.animeapp.presentation.screens.details.DetailScreen
import br.com.mdr.animeapp.presentation.screens.home.HomeScreen
import br.com.mdr.animeapp.presentation.screens.search.SearchScreen
import br.com.mdr.animeapp.presentation.screens.splash.SplashScreen
import br.com.mdr.animeapp.presentation.screens.welcome.WelcomeScreen
import br.com.mdr.animeapp.util.Constants.HERO_ID_KEY

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(name = HERO_ID_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(navController = navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}