package com.oldautumn.movie.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getImageFullUrl(movieImagePath: String, width: Int = 500): String {
        return "https://image.tmdb.org/t/p/w$width$movieImagePath"
    }

    fun getFormatTimeDisplay(timeString: String): String {
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )
        return DateUtils.getRelativeTimeSpanString(format.parse(timeString).time).toString()
    }

    fun fetchFirstCharacter(str: String): String {
        return str.split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .take(2)
            .reduce { acc, s -> acc + s }
    }
}