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
package com.oldautumn.movie.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel
@Inject
constructor(private val repository: AuthRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            SplashUiState(
                false,
                "",
                false,
                DeviceCode("", "", "", 0, 0)
            )
        )
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    private var fetchDeviceCodeJob: Job? = null
    private var fetchDeviceTokenJob: Job? = null

    fun fetchAuthString() {
        fetchJob?.cancel()
        fetchJob =
            viewModelScope.launch {
                try {
                    val authString = repository.authString
                    authString.collect {
                        if (it.isNotEmpty()) {
                            _uiState.value = _uiState.value.copy(
                                authString = it,
                                isAuthed = true
                            )
                        } else {
                            _uiState.value = _uiState.value.copy(
                                authString = it,
                                isAuthed = false
                            )
                        }
                    }
                } catch (ioe: IOException) {
                    // Handle the error and notify the UI when appropriate.
                    _uiState.value = _uiState.value.copy(isAuthed = false, authString = "")
                }
            }
    }

    fun fetchDeviceCode() {
        fetchDeviceCodeJob?.cancel()
        fetchDeviceCodeJob =
            viewModelScope.launch {
                val deviceCode = repository.fetchDeviceCode()
                _uiState.value = _uiState.value.copy(deviceCode = deviceCode)
            }
    }

    fun fetchDeviceToken(deviceCode: String) {
        fetchDeviceTokenJob?.cancel()
        fetchDeviceTokenJob =
            viewModelScope.launch {
                try {
                    val deviceToken = repository.fetchAccessCode(deviceCode)
                    _uiState.value =
                        _uiState.value.copy(
                            isAuthed = true,
                            authString = deviceToken.access_token
                        )
                    repository.updateLocalToken(deviceToken)
                } catch (ioe: IOException) {
                    // Handle the error and notify the UI when appropriate.
                    _uiState.value = _uiState.value.copy(isAuthed = false, authString = "")
                }
            }
    }
}
