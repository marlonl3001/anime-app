package br.com.mdr.animeapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()) {
    val hero = detailsViewModel.hero
}