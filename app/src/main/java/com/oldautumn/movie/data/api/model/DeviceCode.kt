package com.oldautumn.movie.data.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceCode(
    val device_code: String,
    val user_code: String,
    val verification_url: String,
    val expires_in: Int,
    val internal: Int,
) : Parcelable
