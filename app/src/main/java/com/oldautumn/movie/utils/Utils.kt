package com.oldautumn.movie.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Size
import coil.target.ImageViewTarget
import coil.transform.Transformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getImageFullUrl(imagePath: String, width: Int = 500): String {
        return "https://image.tmdb.org/t/p/w$width$imagePath"
    }

    val View.lifecycleOwner get() = ViewTreeLifecycleOwner.get(this)


    inline fun ImageView.loadWithPattle(
        data: Any?,
        imageLoader: ImageLoader = context.imageLoader,
        builder: ImageRequest.Builder.() -> Unit = {},
        crossinline paletteCallback:(Palette) -> Unit
    ): Disposable {
        val request = ImageRequest.Builder(context)
            .apply(builder)
            .data(data)
            .target(this)
            .transformations(object: Transformation {


                override val cacheKey: String
                    get() =  "paletteTransformer"

                override suspend fun transform(input: Bitmap, size: Size): Bitmap {
                    val p = Palette.from(input).generate()

                    paletteCallback(p)

                    return input
                }

            }
            )
            .build()
        return imageLoader.enqueue(request)
    }

    fun getFormatTimeDisplay(timeString: String): String {
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )
        var date: Date? = null
        try {
            date = format.parse(timeString)
        } catch (e: Exception) {

        }
        if (date == null) {
            return ""
        }
        return DateUtils.getRelativeTimeSpanString(date.time).toString()
    }

    fun fetchFirstCharacter(str: String?): String {
        if (str == null || str.isEmpty()) {
            return ""
        }
        return str.split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .take(2)
            .reduce { acc, s -> acc + s }
    }


    //copy from:https://github.com/google/iosched/blob/main/mobile/src/main/java/com/google/samples/apps/iosched/util/UiUtils.kt#L60
    inline fun Fragment.launchAndRepeatWithViewLifecycle(
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
                block()
            }
        }
    }
}