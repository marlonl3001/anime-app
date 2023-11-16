package br.com.mdr.animeapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    //This function converts an image url into a Bitmap
    suspend fun convertImageUrlToBitmap(
        imageUrl: String,
        context: Context
    ): Bitmap? {
        val loader = ImageLoader(context)
        val request = ImageRequest
            .Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val imageResult = loader.execute(request)

        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        val palette = Palette.from(bitmap).generate()
        return mapOf(
            "vibrant" to parseColorSwatch(palette.vibrantSwatch),
            "darkVibrant" to parseColorSwatch(palette.darkVibrantSwatch),
            "onDarkVibrant" to parseBodySwatch(palette.darkVibrantSwatch?.bodyTextColor)
        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            "#$parsedColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodySwatch(color: Int?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color)
            return "#$parsedColor"
        } else {
            "#FFFFFF"
        }
    }
}