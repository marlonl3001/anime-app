package br.com.mdr.animeapp.domain.model

import androidx.annotation.DrawableRes
import br.com.mdr.animeapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    data object First: OnBoardingPage(
        image = R.drawable.greetings,
        title = "Greetings",
        description = "Are you an anime fan? Because if you are it was made for you!"
    )

    data object Second: OnBoardingPage(
        image = R.drawable.explore,
        title = "Explore",
        description = "Find your favorite heroes and learn some of\nthe things that you didn't know about."
    )

    data object Third: OnBoardingPage(
        image = R.drawable.power,
        title = "Power",
        description = "Checkout your hero's power and see how much are\nthey strong comparing to others."
    )
}
