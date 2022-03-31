package com.oldautumn.movie.ui.movie

import com.oldautumn.movie.data.api.model.*

data class MovieReviewUiState(
    val traktReviewList: List<TraktReview>,
    val errorMessage: String? = null
)