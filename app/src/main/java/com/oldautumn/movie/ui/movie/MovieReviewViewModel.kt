package com.oldautumn.movie.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.data.media.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MovieReviewUiState("")

    )

    val uiState: StateFlow<MovieReviewUiState> = _uiState.asStateFlow()

    private val traktMovieId = savedStateHandle.get<String>("traktMovieId") ?: ""
    private val traktMovieTitle = savedStateHandle.get<String>("traktMovieTitle") ?: ""

    val pagingDataFlow: Flow<PagingData<TraktReview>>

    val accept: (UiAction) -> Unit

    init {

        val initialSortType = "newest"
        _uiState.value = _uiState.value.copy(title = traktMovieTitle)

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


    override fun onCleared() {
        savedStateHandle["traktMovieTitle"] = _uiState.value.title
        savedStateHandle["traktMovieId"] = traktMovieId
        super.onCleared()
    }

    sealed class UiAction {
        data class ChangeType(val storyType: String) : UiAction()
    }


}