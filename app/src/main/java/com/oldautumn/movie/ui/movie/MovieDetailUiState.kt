package com.oldautumn.movie.ui.movie

import com.oldautumn.movie.data.api.model.TmdbCreditList
import com.oldautumn.movie.data.api.model.TmdbMovieDetail

data class MovieDetailUiState(
    val movieDetail: TmdbMovieDetail? = null,
    val movieCreditList: TmdbCreditList? = null,
    val errorMessage: String? = null
)