/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oldautumn.movie.utils

import android.graphics.Bitmap
import android.text.format.DateUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Utils {
    fun getImageFullUrl(imagePath: String, width: Int = 500): String =
        "https://image.tmdb.org/t/p/w$width$imagePath"

    inline fun ImageView.loadWithPalette(
        data: Any?,
        imageLoader: ImageLoader = context.imageLoader,
        builder: ImageRequest.Builder.() -> Unit = {},
        crossinline paletteCallback: (Palette) -> Unit
    ): Disposable {
        val request =
            ImageRequest.Builder(context)
                .apply(builder)
                .data(data)
                .target(this)
                .transformations(
                    object : Transformation {
                        override val cacheKey: String
                            get() = "paletteTransformer"

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
        val format =
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.US
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

    // copy from:https://github.com/google/iosched/blob/main/mobile/src/main/java/com/google/samples/apps/iosched/util/UiUtils.kt#L60
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
