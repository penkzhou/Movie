package com.oldautumn.movie.ui.auth

import com.oldautumn.movie.data.api.model.DeviceCode

data class AuthUiState(
    val isDeviceAuthed: Boolean,
    val deviceCode: DeviceCode? = null,
)
