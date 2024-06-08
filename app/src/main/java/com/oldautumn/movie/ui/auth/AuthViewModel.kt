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
package com.oldautumn.movie.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class AuthViewModel
@Inject
constructor(private val repository: AuthRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            AuthUiState(
                false
            )
        )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var fetchDeviceTokenJob: Job? = null

    private var fetchDeviceCodeJob: Job? = null

    fun fetchDeviceCode() {
        fetchDeviceCodeJob?.cancel()
        fetchDeviceCodeJob =
            viewModelScope.launch {
                try {
                    val deviceCode = repository.fetchDeviceCode()

                    _uiState.value =
                        _uiState.value.copy(deviceCode = deviceCode)
                } catch (ioe: IOException) {
                    // Handle the error and notify the UI when appropriate.
                    _uiState.value = _uiState.value.copy(isDeviceAuthed = false)
                } catch (he: HttpException) {
                    _uiState.value = _uiState.value.copy(isDeviceAuthed = false)
                }
            }
    }

    fun fetchDeviceToken(deviceCode: String) {
        fetchDeviceTokenJob?.cancel()
        fetchDeviceTokenJob =
            viewModelScope.launch {
                try {
                    val deviceToken = repository.fetchAccessCode(deviceCode)
                    _uiState.value =
                        _uiState.value.copy(isDeviceAuthed = true)
                    repository.updateLocalToken(deviceToken)
                } catch (ioe: IOException) {
                    // Handle the error and notify the UI when appropriate.
                    _uiState.value = _uiState.value.copy(isDeviceAuthed = false)
                } catch (he: HttpException) {
                    _uiState.value = _uiState.value.copy(isDeviceAuthed = false)
                }
            }
    }
}
