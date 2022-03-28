package com.oldautumn.movie.data.auth

import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.api.model.DeviceToken

const val client_id = "759304793d0a51c6f3164c9e3cc6bebd22402bb0f6442a0bf22cc196e1759b08"
const val client_secret = "c636675d34bc3f4e5c26eb92821f6cbf77d1f5bb8c017b943e39df31d6c206a7"

class AuthRemoteDataSource(
    private val traktApiService: TraktApiService,
) {
    suspend fun fetchDeviceCode(): DeviceCode =
        traktApiService.fetchDeviceCode(client_id)


    suspend fun fetchDeviceAccessToken(deviceCode: String): DeviceToken =
        traktApiService.fetchAccessToken(client_id, client_secret, deviceCode)

}