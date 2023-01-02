package com.oldautumn.movie.data.auth

import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.api.model.DeviceToken
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) {
    val authString: Flow<String> = localDataSource.authFlow

    suspend fun updateLocalToken(authToken: DeviceToken) =
        localDataSource.updateAuthModel(authToken)

    suspend fun fetchDeviceCode(): DeviceCode = remoteDataSource.fetchDeviceCode()

    suspend fun fetchAccessCode(deviceCode: String): DeviceToken =
        remoteDataSource.fetchDeviceAccessToken(deviceCode)
}
