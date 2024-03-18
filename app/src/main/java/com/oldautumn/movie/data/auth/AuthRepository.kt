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
