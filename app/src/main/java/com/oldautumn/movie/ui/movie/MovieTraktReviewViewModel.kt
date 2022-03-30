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

class MovieTraktReviewViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TraktReviewUiState(mutableListOf(), null)

    )
    val uiState: StateFlow<TraktReviewUiState> = _uiState.asStateFlow()


    private var fetchTraktReviewListJob: Job? = null

    fun fetchTraktReviewList(traktMovieId: String, sortType: String = "newest") {
        fetchTraktReviewListJob?.cancel();
        fetchTraktReviewListJob = viewModelScope.launch {
            try {
                val traktReviewList = repository.getTraktReviewList(traktMovieId, sortType)
                _uiState.value = _uiState.value.copy(traktReviewList = traktReviewList)
            } catch (e: IOException) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } catch (hoe: HttpException) {
                _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
            }

        }
    }


}