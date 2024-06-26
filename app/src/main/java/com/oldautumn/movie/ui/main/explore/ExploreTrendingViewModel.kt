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
package com.oldautumn.movie.ui.main.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldautumn.movie.data.media.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class ExploreTrendingViewModel
@Inject
constructor(private val repository: MovieRepository) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ExploreUiState(
                mutableListOf(),
                mutableListOf(),
                ""
            )
        )
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    private var fetchPopularCollectionJob: Job? = null
    private var fetchTrendingCollectionJob: Job? = null

    fun fetchPopularCollection() {
        fetchPopularCollectionJob?.cancel()
        fetchPopularCollectionJob =
            viewModelScope.launch {
                try {
                    val popularMovieList = repository.getTraktPopularCollection()
                    _uiState.value =
                        _uiState.value.copy(
                            popularCollectionList = popularMovieList
                        )
                } catch (ioe: IOException) {
                    // Handle the error and notify the UI when appropriate.
                    _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
                } catch (he: HttpException) {
                    _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
                }
            }
    }

    fun fetchTrendingCollection() {
        fetchTrendingCollectionJob?.cancel()
        fetchTrendingCollectionJob =
            viewModelScope.launch {
                try {
                    val trendingMovieList = repository.getTraktTrendingCollection()
                    _uiState.value =
                        _uiState.value.copy(
                            trendingCollectionList = trendingMovieList
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
