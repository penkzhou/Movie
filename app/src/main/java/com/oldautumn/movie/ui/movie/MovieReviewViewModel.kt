package com.oldautumn.movie.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.data.media.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieReviewViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MovieReviewUiState(mutableListOf(), null)

    )

    val uiState: StateFlow<MovieReviewUiState> = _uiState.asStateFlow()

    private val traktMovieId = savedStateHandle.get<String>("traktMovieId") ?: ""
    private val traktMovieTitle = savedStateHandle.get<String>("traktMovieTitle") ?: ""

    val pagingDataFlow: Flow<PagingData<TraktReview>>

    val accept: (UiAction) -> Unit

    init {

        val initialSortType = "newest"

        val actionStateFlow = MutableSharedFlow<UiAction>()

        val changeType = actionStateFlow
            .filterIsInstance<UiAction.ChangeType>()
            .distinctUntilChanged()
            .onStart {
                emit(UiAction.ChangeType(storyType = initialSortType))
            }
        pagingDataFlow = changeType
            .flatMapConcat { repository.getTraktReviewPageList(traktMovieId, it.storyType) }
            .cachedIn(viewModelScope)

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }


    private var fetchTraktReviewListJob: Job? = null


    fun fetchTraktReviewList(traktMovieId: String, sortType: String = "newest") {
        fetchTraktReviewListJob?.cancel()

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

    override fun onCleared() {

        super.onCleared()
    }

    sealed class UiAction {
        data class ChangeType(val storyType: String) : UiAction()
    }


}