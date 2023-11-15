package br.com.mdr.animeapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.mdr.animeapp.R
import br.com.mdr.animeapp.ui.theme.EXTRA_SMALL_PADDING
import br.com.mdr.animeapp.ui.theme.LightGray
import br.com.mdr.animeapp.ui.theme.StarColor

private const val PATH_BASE_DIVIDE = 2f
private const val FILLED_STARS_KEY = "filledStars"
private const val HALF_FILLED_STARS_KEY = "HalfFilledStars"
private const val EMPTY_STARS_KEY = "emptyStars"

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val result = calculateStars(rating = rating)

    val starPathString = stringResource(id = R.string.star_path)

    //Convert vector string into a Path to draw it inside a Canvas
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)) {
        result[FILLED_STARS_KEY]?.let {
            repeat(it) {
                FilledStar(starPath = starPath, starPathBounds = starPathBounds, scaleFactor = scaleFactor)
            }
        }
        result[HALF_FILLED_STARS_KEY]?.let {
            repeat(it) {
                HalfFilledStar(starPath = starPath, starPathBounds = starPathBounds, scaleFactor = scaleFactor)
            }
        }
        result[EMPTY_STARS_KEY]?.let {
            repeat(it) {
                EmptyStar(starPath = starPath, starPathBounds = starPathBounds, scaleFactor = scaleFactor)
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val pathSize = size

        //Scale lets change paths scale on Canvas
        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (pathSize.width / PATH_BASE_DIVIDE) - (pathWidth / 1.7f)
            val top = (pathSize.height / PATH_BASE_DIVIDE) - (pathHeight / 1.7f)

            //This function allows to align paths drawn on Canvas
            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = StarColor
                )
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val pathSize = size

        //Scale lets change paths scale on Canvas
        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (pathSize.width / PATH_BASE_DIVIDE) - (pathWidth / 1.7f)
            val top = (pathSize.height / PATH_BASE_DIVIDE) - (pathHeight / 1.7f)

            //This function allows to align paths drawn on Canvas
            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val pathSize = size

        //Scale lets change paths scale on Canvas
        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (pathSize.width / PATH_BASE_DIVIDE) - (pathWidth / 1.7f)
            val top = (pathSize.height / PATH_BASE_DIVIDE) - (pathHeight / 1.7f)

            //This function allows to align paths drawn on Canvas
            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
                //Creates a rectangle in front of star path and clips with drawPath format
                clipPath(path = starPath) {
                    //Draws a rectangle
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableIntStateOf(5) }
    var filledStars by remember { mutableIntStateOf(0) }
    var halfFilledStars by remember { mutableIntStateOf(0) }
    var emptyStars by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating
            .toString()
            .split(".")
            .map { it.toInt() }

        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber

            if (lastNumber in 1..5) {
                halfFilledStars++
            }
            if (lastNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && lastNumber > 0) {
                filledStars = 0
                halfFilledStars = 0
                emptyStars = 5
            }
        } else {
            Log.d("RatingWidget", "Invalid rating number")
        }
    }

    emptyStars = maxStars - (filledStars + halfFilledStars)

    return mapOf(
        FILLED_STARS_KEY to filledStars,
        HALF_FILLED_STARS_KEY to halfFilledStars,
        EMPTY_STARS_KEY to emptyStars
    )
}

@Composable
@Preview(showBackground = true)
fun FilledStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    FilledStar(scaleFactor = PATH_BASE_DIVIDE, starPath = starPath, starPathBounds = starPathBounds)
}

@Composable
@Preview(showBackground = true)
fun HalfFilledStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    HalfFilledStar(scaleFactor = PATH_BASE_DIVIDE, starPath = starPath, starPathBounds = starPathBounds)
}

@Composable
@Preview(showBackground = true)
fun EmptyStarPreview() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    EmptyStar(scaleFactor = PATH_BASE_DIVIDE, starPath = starPath, starPathBounds = starPathBounds)
}