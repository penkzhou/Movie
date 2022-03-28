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

    val authFlow: Flow<String> = context.authDataStore.data
        .map { preferences ->
            // No type safety.
            preferences[authStringKey] ?: ""
        }

    suspend fun updateAuthModel(authToken:DeviceToken) {
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