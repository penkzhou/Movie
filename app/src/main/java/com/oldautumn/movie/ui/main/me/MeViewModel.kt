package com.oldautumn.movie.ui.main.me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.auth.AuthRepository
import com.oldautumn.movie.data.media.MovieRepository
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
class MeViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MeUiState(
            false, "", false, null,
            DeviceCode("", "", "", 0, 0)
        )
    )
    val uiState: StateFlow<MeUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    private var fetchDeviceTokenJob: Job? = null
    private var fetchUserInfoJob: Job? = null

    fun fetchAuthString() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val authString = repository.authString
                authString.collect {
                    if (it.isNotEmpty()) {
                        _uiState.value = _uiState.value.copy(authString = it, isAuthed = true)
                    } else {
                        _uiState.value = _uiState.value.copy(authString = it, isAuthed = false)
                    }
                }
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(isAuthed = false, authString = "")
            }
        }
    }

    fun fetchUserInfo(token: String) {
        fetchUserInfoJob?.cancel()
        fetchUserInfoJob = viewModelScope.launch {
            try {
                val userInfo = movieRepository.getUserInfo(token)
                _uiState.value = _uiState.value.copy(userSettings = userInfo)
            } catch (
                ioe: IOException
            ) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(isAuthed = false, userSettings = null)
            } catch (httpException: HttpException) {
                if (httpException.code() == 401) {
                    _uiState.value = _uiState.value.copy(
                        isAuthed = false,
                        userSettings = null,
                        deviceCode = DeviceCode("", "", "", 0, 0)
                    )
                }
            }
        }
    }

    fun fetchDeviceToken(deviceCode: String) {
        fetchDeviceTokenJob?.cancel()
        fetchDeviceTokenJob = viewModelScope.launch {

            try {
                val deviceToken = repository.fetchAccessCode(deviceCode)
                _uiState.value =
                    _uiState.value.copy(isAuthed = true, authString = deviceToken.access_token)
                repository.updateLocalToken(deviceToken)
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(isAuthed = false, authString = "")
            }
        }
    }
}
