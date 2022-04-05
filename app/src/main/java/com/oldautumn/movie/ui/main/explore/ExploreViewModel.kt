package com.oldautumn.movie.ui.main.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.media.MovieRepository
import com.oldautumn.movie.ui.main.home.HomeUiState
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
class ExploreViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ExploreUiState(
            mutableListOf(),
            ""
        )
    )
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private var fetchPopularCollectionJob: Job? = null


    fun fetchPopularCollection() {
        fetchPopularCollectionJob?.cancel()
        fetchPopularCollectionJob = viewModelScope.launch {
            try {
                val popularMovieList = repository.getTraktPopularCollection()
                _uiState.value = _uiState.value.copy(
                    collectionList = popularMovieList
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