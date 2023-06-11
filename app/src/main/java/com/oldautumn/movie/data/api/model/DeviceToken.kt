package com.oldautumn.movie.data.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceToken(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val scope: String,
    val created_at: Long,
    val expires_in: Int
) : Parcelable
