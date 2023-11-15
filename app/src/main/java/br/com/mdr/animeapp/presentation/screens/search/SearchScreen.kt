package br.com.mdr.animeapp.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.mdr.animeapp.presentation.common.ListContent
import br.com.mdr.animeapp.ui.theme.MEDIUM_PADDING

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val searchQuery by viewModel.searchQuery

    val heroes = viewModel.heroes.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = { viewModel.updateSearchQuery(it) },
                onSearchClicked = {
                    viewModel.searchHeroes(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
            ) {
                ListContent(heroes = heroes, navController = navController)
            }
        }
    )
}