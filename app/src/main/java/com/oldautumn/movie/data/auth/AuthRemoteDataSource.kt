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

import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.api.model.DeviceToken

const val CLIENT_ID = "759304793d0a51c6f3164c9e3cc6bebd22402bb0f6442a0bf22cc196e1759b08"
const val CLIENT_SECRET = "c636675d34bc3f4e5c26eb92821f6cbf77d1f5bb8c017b943e39df31d6c206a7"

class AuthRemoteDataSource(
    private val traktApiService: TraktApiService,
) {
    suspend fun fetchDeviceCode(): DeviceCode = traktApiService.fetchDeviceCode(CLIENT_ID)

    suspend fun fetchDeviceAccessToken(deviceCode: String): DeviceToken =
        traktApiService.fetchAccessToken(CLIENT_ID, CLIENT_SECRET, deviceCode)
}
