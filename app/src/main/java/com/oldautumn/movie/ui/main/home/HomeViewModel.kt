package com.oldautumn.movie.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(

    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            listOf(), mutableListOf(), ""
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var fetchListJob: Job? = null


    fun fetchMovieData() {
        fetchListJob?.cancel()
        fetchListJob = viewModelScope.launch {
            try {
                val trendingMovieList = repository.getTrendingMovieList()
                val popularMovieList = repository.getPopularMovieList()
                _uiState.value = _uiState.value.copy(
                    trendingList = trendingMovieList,
                    popularList = popularMovieList
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