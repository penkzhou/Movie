/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oldautumn.movie.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.data.media.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

const val DEFAULT_SORT_TYPE = "newest"

@HiltViewModel
class MovieReviewViewModel
@Inject
constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val traktMovieId = savedStateHandle.get<String>("traktMovieId") ?: ""
    private val traktMovieTitle = savedStateHandle.get<String>("traktMovieTitle") ?: ""
    private val _uiState =
        MutableStateFlow(
            MovieReviewUiState(title = traktMovieTitle)
        )

    val uiState: StateFlow<MovieReviewUiState> = _uiState.asStateFlow()

    val initialSortType: String = savedStateHandle.get("initialSortType") ?: DEFAULT_SORT_TYPE

    val pagingDataFlow: Flow<PagingData<TraktReview>>

    val accept: (UiAction) -> Unit

    init {

        val actionStateFlow = MutableSharedFlow<UiAction>()

        val changeType =
            actionStateFlow
                .filterIsInstance<UiAction.ChangeType>()
                .distinctUntilChanged()
                .onStart {
                    emit(UiAction.ChangeType(sortType = initialSortType))
                }

        pagingDataFlow =
            changeType
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
