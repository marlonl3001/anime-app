package br.com.mdr.animeapp.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.mdr.animeapp.R
import br.com.mdr.animeapp.domain.model.OnBoardingPage
import br.com.mdr.animeapp.navigation.Screen
import br.com.mdr.animeapp.ui.theme.EXTRA_LARGE_PADDING
import br.com.mdr.animeapp.ui.theme.PAGING_INDICATOR_HEIGHT
import br.com.mdr.animeapp.ui.theme.PAGING_INDICATOR_SPACING
import br.com.mdr.animeapp.ui.theme.PAGING_INDICATOR_WIDTH
import br.com.mdr.animeapp.ui.theme.activeIndicatorColor
import br.com.mdr.animeapp.ui.theme.buttonBackgroundColor
import br.com.mdr.animeapp.ui.theme.descriptionColor
import br.com.mdr.animeapp.ui.theme.inactiveIndicatorColor
import br.com.mdr.animeapp.ui.theme.titleColor
import br.com.mdr.animeapp.ui.theme.welcomeScreenBackgroundColor
import br.com.mdr.animeapp.util.Constants.LAST_ONBOARDING_PAGE

@Composable
fun WelcomeScreen(
    navController: NavController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()) {
    Welcome(navController, welcomeViewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Welcome(
    navController: NavController,
    welcomeViewModel: WelcomeViewModel) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState(pageCount = {pages.size})

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.welcomeScreenBackgroundColor)) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) {position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        PagerIndicator(pageCount = pages.size, pagerState = pagerState, modifier = Modifier.weight(1f))
        FinishButton(pagerState = pagerState, modifier = Modifier.weight(1f)) {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
            welcomeViewModel.saveOnBoardingState(true)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == LAST_ONBOARDING_PAGE) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.buttonBackgroundColor
                )) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.finish_button))
            }
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = onBoardingPage.title)

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.titleColor,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            text = onBoardingPage.title,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.descriptionColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            text = onBoardingPage.description,
            textAlign = TextAlign.Center)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(pageCount: Int, pagerState: PagerState, modifier: Modifier) {
    Row(
        modifier
            .height(PAGING_INDICATOR_HEIGHT)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration)
                    MaterialTheme.colorScheme.activeIndicatorColor
                else
                    MaterialTheme.colorScheme.inactiveIndicatorColor
            Box(
                modifier = Modifier
                    .padding(PAGING_INDICATOR_SPACING)
                    .clip(CircleShape)
                    .background(color)
                    .size(PAGING_INDICATOR_WIDTH)
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun WelcomeScreenDarkPreview() {
//    Welcome()
//}
