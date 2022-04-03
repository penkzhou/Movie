package com.oldautumn.movie.ui.main.tv

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
class TvMainViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TvMainUiState(
            mutableListOf(),
            mutableListOf(),
            ""
        )
    )
    val uiState: StateFlow<TvMainUiState> = _uiState.asStateFlow()

    private var fetchPopularShowListJob: Job? = null
    private var fetchTrendingShowListJob: Job? = null




    fun fetchPopularShow() {
        fetchPopularShowListJob?.cancel()
        fetchPopularShowListJob = viewModelScope.launch {
            try {
                val popularMovieList = repository.getPopularShowList()
                _uiState.value = _uiState.value.copy(
                    popularShowList = popularMovieList
                )
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
            } catch (he: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
            }
        }
    }

    fun fetchTrendingShow() {
        fetchTrendingShowListJob?.cancel()
        fetchTrendingShowListJob = viewModelScope.launch {
            try {
                val trendingMovieList = repository.getTrendingShowList()
                _uiState.value = _uiState.value.copy(
                    trendingShowList = trendingMovieList
                )
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
            } catch (he: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
            }
        }
    }

}