package com.oldautumn.movie.ui.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.media.MovieRepository
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
class TvSeasonDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val tvId = savedStateHandle["tvId"] ?: 0
    private val seasonNumber = savedStateHandle["seasonNumber"] ?: 0
    private val _uiState = MutableStateFlow(
        TvSeasonDetailUiState(),
    )
    val uiState: StateFlow<TvSeasonDetailUiState> = _uiState.asStateFlow()

    private var fetchTvSeasonDetailJob: Job? = null

    fun fetchTvSeasonDetail() {
        if (tvId > 0 && seasonNumber > 0) {
            fetchTvSeasonDetail(tvId, seasonNumber)
        }
    }

    private fun fetchTvSeasonDetail(tvId: Int, seasonNumber: Int) {
        fetchTvSeasonDetailJob?.cancel()
        fetchTvSeasonDetailJob = viewModelScope.launch {
            try {
                val tvDetail = repository.getShowSeasonDetail(tvId, seasonNumber)
                _uiState.value = _uiState.value.copy(tvSeasonDetail = tvDetail)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }
        }
    }

    override fun onCleared() {
        savedStateHandle["tvId"] = tvId
        super.onCleared()
    }
}
