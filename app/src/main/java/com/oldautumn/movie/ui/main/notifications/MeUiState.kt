package com.oldautumn.movie.ui.main.notifications

import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.api.model.UserSettings

data class MeUiState(
    val isAuthed: Boolean,
    val authString: String,
    val isDeviceAuthed: Boolean,
    val userSettings: UserSettings? = null,
    val deviceCode: DeviceCode
)
