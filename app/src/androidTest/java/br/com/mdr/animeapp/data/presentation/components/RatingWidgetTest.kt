package br.com.mdr.animeapp.data.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import br.com.mdr.animeapp.presentation.components.RatingWidget
import br.com.mdr.animeapp.ui.theme.SMALL_PADDING
import org.junit.Rule
import org.junit.Test

class RatingWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun passZeroPointZeroValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 0.0
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(0)
    }

    @Test
    fun passZeroPointFiveValue_Assert_FourEmptyStars_and_OneHalfFilledStar() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 0.5
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(0)
    }

    @Test
    fun passZeroPointSixValue_Assert_FourEmptyStars_and_OneFilledStar() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 0.6
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(1)
    }

    @Test
    fun passFourPointZeroValue_Assert_FourFilledStars_and_OneEmptyStar() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 4.0
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(4)
    }

    @Test
    fun passFourPointThreeValue_Assert_FourFilledStars_and_OneHalfFilledStar() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 4.3
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(4)
    }

    @Test
    fun passNegativeValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = -1.5
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(0)
    }

    @Test
    fun passInvalidValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(
                modifier = Modifier.padding(all = SMALL_PADDING),
                rating = 5.1
            )
        }
        composeTestRule.onAllNodesWithContentDescription("emptyStars")
            .assertCountEquals(5)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStars")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("filledStars")
            .assertCountEquals(0)
    }
}