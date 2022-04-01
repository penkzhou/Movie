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

const val DEFAULT_SORT_TYPE = "newest"

@HiltViewModel
class MovieReviewViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val traktMovieId = savedStateHandle.get<String>("traktMovieId") ?: ""
    private val traktMovieTitle = savedStateHandle.get<String>("traktMovieTitle") ?: ""
    private val _uiState = MutableStateFlow(
        MovieReviewUiState(title = traktMovieTitle)
    )

    val uiState: StateFlow<MovieReviewUiState> = _uiState.asStateFlow()

    val initialSortType: String = savedStateHandle.get("initialSortType") ?: DEFAULT_SORT_TYPE


    val pagingDataFlow: Flow<PagingData<TraktReview>>

    val accept: (UiAction) -> Unit

    init {


        val actionStateFlow = MutableSharedFlow<UiAction>()

        val changeType = actionStateFlow
            .filterIsInstance<UiAction.ChangeType>()
            .distinctUntilChanged()
            .onStart {
                emit(UiAction.ChangeType(sortType = initialSortType))
            }

        pagingDataFlow = changeType
            .flatMapConcat {
                repository.getTraktReviewPageList(traktMovieId, it.sortType)
            }
            .cachedIn(viewModelScope)





        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }
    }


    override fun onCleared() {
        savedStateHandle["traktMovieTitle"] = uiState.value.title
        savedStateHandle["traktMovieId"] = traktMovieId
        savedStateHandle["initialSortType"] = initialSortType
        super.onCleared()
    }

    sealed class UiAction {
        data class ChangeType(val sortType: String) : UiAction()
    }


}

