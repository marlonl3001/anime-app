package br.com.mdr.animeapp.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.mdr.animeapp.R
import br.com.mdr.animeapp.domain.model.Anime
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.presentation.components.InfoBox
import br.com.mdr.animeapp.presentation.components.OrderedList
import br.com.mdr.animeapp.ui.theme.INFO_ICON_SIZE
import br.com.mdr.animeapp.ui.theme.LARGE_PADDING
import br.com.mdr.animeapp.ui.theme.MEDIUM_PADDING
import br.com.mdr.animeapp.ui.theme.MIN_SHEET_HEIGHT
import br.com.mdr.animeapp.ui.theme.SMALL_PADDING
import br.com.mdr.animeapp.ui.theme.titleColor
import br.com.mdr.animeapp.util.Constants
import br.com.mdr.animeapp.util.Constants.BASE_URL
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navController: NavController,
    selectedHero: Hero?
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let { BottomSheetContent(selectedHero = it) }
        },
        content = {
            selectedHero?.let {
                BackgroundContent(heroImage = it.image,
                    onCloseClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }
    )
}

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colorScheme.primary,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE),
                painter = painterResource(id = getHeroIcon(selectedHero)),
                contentDescription = stringResource(id = R.string.info_icon),
                tint = contentColor)

            Text(
                modifier = Modifier
                    .padding(start = SMALL_PADDING),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(id = R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(id = R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.day,
                smallText = stringResource(id = R.string.birthday),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            maxLines = Constants.ABOUT_TEXT_MAX_LINES
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    onCloseClicked: () -> Unit
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data("${BASE_URL}${heroImage}")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .size(Size.ORIGINAL)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { onCloseClicked }) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White)
            }
        }
    }
}

private fun getHeroIcon(selectedHero: Hero): Int =
    when {
        selectedHero.anime.contains(Anime.NARUTO.name) -> R.drawable.ic_logo
        selectedHero.anime.contains(Anime.ONE_PIECE.name) -> R.drawable.ic_one_piece
        else -> R.drawable.ic_logo
    }

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 1,
            anime = Anime.ONE_PIECE.name,
            name = "Monkey D. Luffy",
            image = "/images/luffy.jpg",
            about = "Monkey D. Luffy, also known as \"Straw Hat Luffy\" and commonly as \"Straw Hat\",[10] is the founder and captain of the increasingly infamous and powerful Straw Hat Pirates, as well as the most powerful of its top fighters.[26][27] He desires to find the legendary treasure left behind by the late Gol D. Roger and thereby become the Pirate King,[28] which would help facilitate an unknown dream of his that he has told only to Shanks, his brothers, and crew.[29][30] He believes that being the Pirate King means having the most freedom in the world.[31]",
            rating = 5.0,
            power = 105,
            month = "May",
            day = "5th",
            family = listOf("Monkey D. Dragon", "Monkey D. Garp", "Portgas D. Ace", "Curly Dadan", "Sabo", "Family Tree"),
            abilities = listOf("Rifle", "Ogon Rifle", "Elephant Gun", "Kane", "Red Hawk"),
            natureTypes = listOf( "Rubber", "Fire")
        )
    )
}