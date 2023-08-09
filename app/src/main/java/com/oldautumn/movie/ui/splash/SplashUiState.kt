package com.oldautumn.movie.ui.splash

import com.oldautumn.movie.data.api.model.DeviceCode

data class SplashUiState(
    val isAuthed: Boolean,
    val authString: String,
    val isDeviceAuthed: Boolean,
    val deviceCode: DeviceCode,
)
