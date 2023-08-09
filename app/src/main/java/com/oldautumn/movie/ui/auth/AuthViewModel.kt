package com.oldautumn.movie.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            false,
        ),
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var fetchDeviceTokenJob: Job? = null

    private var fetchDeviceCodeJob: Job? = null

    fun fetchDeviceCode() {
        fetchDeviceCodeJob?.cancel()
        fetchDeviceCodeJob = viewModelScope.launch {
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
        fetchDeviceTokenJob = viewModelScope.launch {
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
