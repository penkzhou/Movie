package com.oldautumn.movie.ui.movie

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
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get("movieId") ?: 0
    private var movieSlug = savedStateHandle.get("movieSlug") ?: ""

    private val _uiState = MutableStateFlow(
        MovieDetailUiState()

    )
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()


    private var fetchMovieDetailJob: Job? = null
    private var fetchMovieCreditJob: Job? = null
    private var fetchTraktMovieDetailJob: Job? = null
    private var fetchRecommendMovieListJob: Job? = null
    private var fetchSimilarMovieListJob: Job? = null

    fun fetchMovieDetailData() {
        if (movieId > 0) {
            fetchMovieDetail(movieId)
            fetchMovieCredit(movieId)
            fetchRecommendMovieList(movieId)
            fetchSimilarMovieList(movieId)
        }
        if (movieSlug.isNotEmpty()) {
            fetchTraktMovieDetail(movieSlug)
        }
    }

    private fun fetchMovieDetail(movieId: Int) {
        fetchMovieDetailJob?.cancel();
        fetchMovieDetailJob = viewModelScope.launch {
            try {
                val movieDetail = repository.getMovieDetail(movieId)
                _uiState.value = _uiState.value.copy(movieDetail = movieDetail)
                if (movieSlug.isNullOrEmpty()) {
                    movieSlug = movieDetail.imdb_id ?: ""
                    fetchTraktMovieDetail(movieSlug)
                }
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }


    private fun fetchMovieCredit(movieId: Int) {
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

    private fun fetchTraktMovieDetail(movieSlug: String) {
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

    private fun fetchRecommendMovieList(movieId: Int) {
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

    private fun fetchSimilarMovieList(movieId: Int) {
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

    override fun onCleared() {
        savedStateHandle["movieId"] = movieId
        savedStateHandle["movieSlug"] = movieSlug
        super.onCleared()
    }

}