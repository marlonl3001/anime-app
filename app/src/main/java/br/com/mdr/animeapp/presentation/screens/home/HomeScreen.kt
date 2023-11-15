package br.com.mdr.animeapp.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.mdr.animeapp.presentation.common.ListContent
import br.com.mdr.animeapp.ui.theme.MEDIUM_PADDING

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController) {

    /**
     * Collects values from this Flow of PagingData and represents them inside a LazyPagingItems instance.
     * The LazyPagingItems instance can be used for lazy foundations such as LazyListScope.items in order to
     * display the data obtained from a Flow of PagingData.
     * **/
    val heroes = viewModel.heroes.collectAsLazyPagingItems()

    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClick = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
        ) {
            ListContent(heroes = heroes, navController = navController)
        }
    }
}