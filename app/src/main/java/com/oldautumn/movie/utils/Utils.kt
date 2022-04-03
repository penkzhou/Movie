package com.oldautumn.movie.utils

import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getImageFullUrl(imagePath: String, width: Int = 500): String {
        return "https://image.tmdb.org/t/p/w$width$imagePath"
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

    fun fetchFirstCharacter(str: String): String {
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