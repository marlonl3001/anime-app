package br.com.mdr.animeapp.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.mdr.animeapp.R
import br.com.mdr.animeapp.ui.theme.topAppBarBackgroundColor
import br.com.mdr.animeapp.ui.theme.topAppBarContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onSearchClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Explore", color = MaterialTheme.colorScheme.topAppBarContentColor)
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
            actionIconContentColor = MaterialTheme.colorScheme.topAppBarContentColor
        )
    )
}

@Composable
@Preview
fun HomeTopAppBarPreview() {
    HomeTopBar {

    }
}