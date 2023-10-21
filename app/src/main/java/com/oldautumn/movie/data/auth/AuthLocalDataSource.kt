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

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.oldautumn.movie.data.api.model.DeviceToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.authDataStore by preferencesDataStore("settings")

class AuthLocalDataSource(private val context: Context) {
    private val authStringKey = stringPreferencesKey("auth_string_key")
    private val scope = stringPreferencesKey("scope")
    private val refreshToken = stringPreferencesKey("refresh_token")
    private val createdAt = longPreferencesKey("created_at")
    private val expiresIn = intPreferencesKey("expires_in")
    private val tokenType = stringPreferencesKey("token_type")

    val authFlow: Flow<String> =
        context.authDataStore.data
            .map { preferences ->
                // No type safety.
                preferences[authStringKey] ?: ""
            }

    suspend fun updateAuthModel(authToken: DeviceToken) {
        context.authDataStore.edit { settings ->
            settings[authStringKey] = authToken.access_token
            settings[scope] = authToken.scope
            settings[refreshToken] = authToken.refresh_token
            settings[createdAt] = authToken.created_at
            settings[expiresIn] = authToken.expires_in
            settings[tokenType] = authToken.token_type
        }
    }
}
