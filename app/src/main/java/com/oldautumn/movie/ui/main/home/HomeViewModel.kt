package com.oldautumn.movie.ui.main.home

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
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            "",
        ),
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var fetchTrendingMovieListJob: Job? = null
    private var fetchPopularMovieListJob: Job? = null
    private var fetchBoxOfficeMovieListJob: Job? = null

    fun fetchPopularMovie() {
        fetchPopularMovieListJob?.cancel()
        fetchPopularMovieListJob = viewModelScope.launch {
            try {
                val popularMovieList = repository.getPopularMovieList()
                _uiState.value = _uiState.value.copy(
                    popularMovieList = popularMovieList,
                )
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
            } catch (he: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
            }
        }
    }

    fun fetchBoxOfficeMovieList() {
        fetchBoxOfficeMovieListJob?.cancel()
        fetchBoxOfficeMovieListJob = viewModelScope.launch {
            try {
                val boxOfficeMovieList = repository.getTraktBoxOffice()
                _uiState.value = _uiState.value.copy(
                    revenueMovieList = boxOfficeMovieList,
                )
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
            } catch (he: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
            }
        }
    }

    fun fetchMovieData() {
        fetchTrendingMovieListJob?.cancel()
        fetchTrendingMovieListJob = viewModelScope.launch {
            try {
                val trendingMovieList = repository.getTrendingMovieList()
                _uiState.value = _uiState.value.copy(
                    trendingMovieList = trendingMovieList,
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
