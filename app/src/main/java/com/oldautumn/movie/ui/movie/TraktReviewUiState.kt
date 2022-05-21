package com.oldautumn.movie.ui.movie

import com.oldautumn.movie.data.api.model.TraktReview

data class TraktReviewUiState(
    val traktReviewList: List<TraktReview> = listOf(),
    val errorMessage: String? = null
)