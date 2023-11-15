package br.com.mdr.animeapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import br.com.mdr.animeapp.R
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.navigation.Screen
import br.com.mdr.animeapp.presentation.components.RatingWidget
import br.com.mdr.animeapp.presentation.components.ShimmerEffect
import br.com.mdr.animeapp.ui.theme.HERO_ITEM_HEIGHT
import br.com.mdr.animeapp.ui.theme.LARGE_PADDING
import br.com.mdr.animeapp.ui.theme.MEDIUM_PADDING
import br.com.mdr.animeapp.ui.theme.SMALL_PADDING
import br.com.mdr.animeapp.ui.theme.topAppBarContentColor
import br.com.mdr.animeapp.util.Constants
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
    val result = handlePagingResult(heroes = heroes)

    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(count = heroes.itemCount) {index ->
                heroes[index]?.let { hero ->
                    HeroItem(hero = hero, navController = navController)
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(heroes: LazyPagingItems<Hero>): Boolean {
    heroes.apply {
        val error = when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error)
                false
            }
            else -> true
        }
    }
}

@Composable
fun HeroItem(
    hero: Hero,
    navController: NavHostController
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data("${Constants.BASE_URL}${hero.image}")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .size(Size.ORIGINAL)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passHeroId(hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(id = R.string.hero_image),
                contentScale = ContentScale.Crop)
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colorScheme.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = hero.getAnimeName(),
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .padding(end = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HeroItemPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Monkey D. Luffy",
            anime = "One Piece",
            image = "${Constants.BASE_URL}/image/naruto.jpg",
            about = "Monkey D. Luffy. Kayzoku oni, ore wa naruuuuuuuuu.\nLorem ipsum\nDale dale dale",
            rating = 4.3,
            power = 100,
            month = "01",
            day = "16",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController())
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemDarkPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Monkey D. Luffy",
            anime = "One Piece",
            image = "${Constants.BASE_URL}/image/naruto.jpg",
            about = "Monkey D. Luffy. Kayzoku oni, ore wa naruuuuuuuuu.\nLorem ipsum\nDale dale dale",
            rating = 4.3,
            power = 100,
            month = "01",
            day = "16",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController())
}