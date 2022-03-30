package com.oldautumn.movie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.media.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieDetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MovieDetailUiState(null, null)

    )
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()


    private var fetchMovieDetailJob: Job? = null
    private var fetchMovieCreditJob: Job? = null
    private var fetchTraktMovieDetailJob: Job? = null
    private var fetchRecommendMovieListJob: Job? = null
    private var fetchSimilarMovieListJob: Job? = null

    fun fetchMovieDetail(movieId: Int) {
        fetchMovieDetailJob?.cancel();
        fetchMovieDetailJob = viewModelScope.launch {
            try {
                val movieDetail = repository.getMovieDetail(movieId)
                _uiState.value = _uiState.value.copy(movieDetail = movieDetail)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }


    fun fetchMovieCredit(movieId: Int) {
        fetchMovieCreditJob?.cancel();
        fetchMovieCreditJob = viewModelScope.launch {
            try {
                val movieCredit = repository.getMovieCredits(movieId)
                _uiState.value = _uiState.value.copy(movieCreditList = movieCredit)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }

    fun fetchTraktMovieDetail(movieSlug: String) {
        fetchTraktMovieDetailJob?.cancel()
        fetchTraktMovieDetailJob = viewModelScope.launch {
            try {
                val traktMovieDetail = repository.getTraktMovieDetail(movieSlug)
                _uiState.value = _uiState.value.copy(traktMovieDetail = traktMovieDetail)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }

    fun fetchRecommendMovieList(movieId: Int){
        fetchRecommendMovieListJob?.cancel()
        fetchRecommendMovieListJob = viewModelScope.launch {
            try {
                val recommendMovieList = repository.getRecommendMovieList(movieId)
                _uiState.value = _uiState.value.copy(recommendMovieList = recommendMovieList)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }
        }
    }

    fun fetchSimilarMovieList(movieId: Int){
        fetchSimilarMovieListJob?.cancel()
        fetchSimilarMovieListJob = viewModelScope.launch {
            try {
                val similarMovieList = repository.getSimilarMovieList(movieId)
                _uiState.value = _uiState.value.copy(similarMovieList = similarMovieList)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }
        }
    }

}