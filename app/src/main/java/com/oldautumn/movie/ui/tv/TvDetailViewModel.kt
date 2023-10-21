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
package com.oldautumn.movie.ui.tv

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
class TvDetailViewModel
    @Inject
    constructor(
        private val repository: MovieRepository,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val tvId = savedStateHandle.get("tvId") ?: 0

        private val _uiState =
            MutableStateFlow(
                TvDetailUiState(),
            )
        val uiState: StateFlow<TvDetailUiState> = _uiState.asStateFlow()

        private var fetchTvDetailJob: Job? = null
        private var fetchTvCreditJob: Job? = null
        private var fetchTraktTvDetailJob: Job? = null
        private var fetchTraktTvRatingJob: Job? = null
        private var fetchRecommendTvListJob: Job? = null
        private var fetchSimilarTvListJob: Job? = null

        fun fetchTvDetailData() {
            if (tvId > 0) {
                fetchTvDetail(tvId)
                fetchTvCredit(tvId)
                fetchRecommendTvList(tvId)
                fetchSimilarTvList(tvId)
            }
        }

        fun currentTvId() = tvId

        private fun fetchTraktTvRating(tvImdbId: String) {
            fetchTraktTvRatingJob?.cancel()
            fetchTraktTvRatingJob =
                viewModelScope.launch {
                    try {
                        val rating = repository.getTraktTvRating(tvImdbId)
                        _uiState.value =
                            _uiState.value.copy(
                                traktRating = rating,
                            )
                    } catch (e: IOException) {
                        _uiState.value =
                            _uiState.value.copy(
                                errorMessage = e.message,
                            )
                    } catch (e: HttpException) {
                        _uiState.value =
                            _uiState.value.copy(
                                errorMessage = e.message,
                            )
                    }
                }
        }

        private fun fetchTvDetail(tvId: Int) {
            fetchTvDetailJob?.cancel()
            fetchTvDetailJob =
                viewModelScope.launch {
                    try {
                        val tvDetail = repository.getShowDetail(tvId)
                        _uiState.value = _uiState.value.copy(tvDetail = tvDetail)
                        if (tvDetail.external_ids.imdb_id != null) {
                            fetchTraktTvDetail(tvDetail.external_ids.imdb_id)
                            fetchTraktTvRating(tvDetail.external_ids.imdb_id)
                        }
                    } catch (e: IOException) {
                        _uiState.value = _uiState.value.copy(errorMessage = e.message)
                    } catch (hoe: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
                    }
                }
        }

        private fun fetchTvCredit(tvId: Int) {
            fetchTvCreditJob?.cancel()
            fetchTvCreditJob =
                viewModelScope.launch {
                    try {
                        val tvCredit = repository.getShowCredits(tvId)
                        _uiState.value = _uiState.value.copy(tvCreditList = tvCredit)
                    } catch (e: IOException) {
                        _uiState.value = _uiState.value.copy(errorMessage = e.message)
                    } catch (hoe: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
                    }
                }
        }

        private fun fetchTraktTvDetail(tvSlug: String) {
            fetchTraktTvDetailJob?.cancel()
            fetchTraktTvDetailJob =
                viewModelScope.launch {
                    try {
                        val traktTvDetail = repository.getTraktTvDetail(tvSlug)
                        _uiState.value = _uiState.value.copy(traktTvDetail = traktTvDetail)
                    } catch (e: IOException) {
                        _uiState.value = _uiState.value.copy(errorMessage = e.message)
                    } catch (hoe: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
                    }
                }
        }

        private fun fetchRecommendTvList(tvId: Int) {
            fetchRecommendTvListJob?.cancel()
            fetchRecommendTvListJob =
                viewModelScope.launch {
                    try {
                        val recommendTvList = repository.getRecommendTvList(tvId)
                        _uiState.value = _uiState.value.copy(recommendTvList = recommendTvList)
                    } catch (e: IOException) {
                        _uiState.value = _uiState.value.copy(errorMessage = e.message)
                    } catch (hoe: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
                    }
                }
        }

        private fun fetchSimilarTvList(tvId: Int) {
            fetchSimilarTvListJob?.cancel()
            fetchSimilarTvListJob =
                viewModelScope.launch {
                    try {
                        val similarTvList = repository.getSimilarTvList(tvId)
                        _uiState.value = _uiState.value.copy(similarTvList = similarTvList)
                    } catch (e: IOException) {
                        _uiState.value = _uiState.value.copy(errorMessage = e.message)
                    } catch (hoe: HttpException) {
                        _uiState.value = _uiState.value.copy(errorMessage = hoe.message)
                    }
                }
        }

        override fun onCleared() {
            savedStateHandle["tvId"] = tvId
            super.onCleared()
        }
    }
