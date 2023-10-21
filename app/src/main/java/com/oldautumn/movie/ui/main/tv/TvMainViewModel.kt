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
package com.oldautumn.movie.ui.main.tv

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
class TvMainViewModel
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                TvMainUiState(
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                    "",
                ),
            )
        val uiState: StateFlow<TvMainUiState> = _uiState.asStateFlow()

        private var fetchPopularShowListJob: Job? = null
        private var fetchTrendingShowListJob: Job? = null
        private var fetchMostRecommendShowListJob: Job? = null
        private var fetchMostPlayedShowListJob: Job? = null

        fun fetchPopularShow() {
            fetchPopularShowListJob?.cancel()
            fetchPopularShowListJob =
                viewModelScope.launch {
                    try {
                        val popularMovieList = repository.getPopularShowList()
                        _uiState.value =
                            _uiState.value.copy(
                                popularShowList = popularMovieList,
                            )
                    } catch (ioe: IOException) {
                        // Handle the error and notify the UI when appropriate.
                        _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
                    } catch (he: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
                    }
                }
        }

        fun fetchTrendingShow() {
            fetchTrendingShowListJob?.cancel()
            fetchTrendingShowListJob =
                viewModelScope.launch {
                    try {
                        val trendingMovieList = repository.getTrendingShowList()
                        _uiState.value =
                            _uiState.value.copy(
                                trendingShowList = trendingMovieList,
                            )
                    } catch (ioe: IOException) {
                        // Handle the error and notify the UI when appropriate.
                        _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
                    } catch (he: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
                    }
                }
        }

        fun fetchMostRecommendShowList(period: String) {
            fetchMostRecommendShowListJob?.cancel()
            fetchMostRecommendShowListJob =
                viewModelScope.launch {
                    try {
                        val mostRecommendShowList = repository.getMostRecommendShowList(period)
                        _uiState.value =
                            _uiState.value.copy(
                                mostRecommendShowList = mostRecommendShowList,
                            )
                    } catch (ioe: IOException) {
                        // Handle the error and notify the UI when appropriate.
                        _uiState.value = _uiState.value.copy(errorMessage = "数据解析异常")
                    } catch (he: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = "网络异常")
                    }
                }
        }

        fun fetchMostPlayedShowList(period: String) {
            fetchMostPlayedShowListJob?.cancel()
            fetchMostPlayedShowListJob =
                viewModelScope.launch {
                    try {
                        val mostPlayedShowList = repository.getMostPlayedShowList(period)
                        _uiState.value =
                            _uiState.value.copy(
                                mostPlayedShowList = mostPlayedShowList,
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
