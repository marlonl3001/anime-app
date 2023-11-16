package br.com.mdr.animeapp.presentation.screens.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()) {

    val hero by detailsViewModel.hero.collectAsState()

    DetailsContent(
        navController = navController,
        selectedHero = hero
    )
}