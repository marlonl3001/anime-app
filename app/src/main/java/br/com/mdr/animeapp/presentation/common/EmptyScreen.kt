package br.com.mdr.animeapp.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import br.com.mdr.animeapp.R
import br.com.mdr.animeapp.domain.model.Hero
import br.com.mdr.animeapp.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import br.com.mdr.animeapp.ui.theme.SMALL_PADDING
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<Hero>? = null) {
    var message by remember {
        mutableStateOf("Find your Favorite Hero!")
    }
    var icon by remember {
        mutableIntStateOf(R.drawable.ic_search_document)
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ), label = "alphaAnim"
    )

    if (error != null) {
       message = getErrorMessage(error)
       icon = R.drawable.ic_network_error
    }

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
        alphaAnim = alphaAnim,
        icon = icon,
        message = message,
        heroes = heroes
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyContent(
    alphaAnim: Float,
    icon: Int,
    message: String,
    heroes: LazyPagingItems<Hero>? = null) {

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember {
        mutableStateOf(false)
    }

    fun refresh() = refreshScope.launch {
        refreshing = true
        heroes?.refresh()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    Box(Modifier
        .pullRefresh(state)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(NETWORK_ERROR_ICON_HEIGHT)
                    .alpha(alphaAnim),
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.network_error_icon),
                tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            )
            Text(
                modifier = Modifier
                    .padding(top = SMALL_PADDING)
                    .alpha(alphaAnim),
                text = message,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = state
        )
    }
}

private fun getErrorMessage(errorState: LoadState.Error): String {
    return when (errorState.error) {
        is SocketTimeoutException -> "Server Unavailable."
        is ConnectException -> "Internet Unavailable."
        is NullPointerException -> "No Hero found!"
        else -> "Unknown error."
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    EmptyContent(
        alphaAnim = 1f,
        icon = R.drawable.ic_network_error,
        message = "Internet Error")
}