package com.oldautumn.movie.ui.movie

import com.oldautumn.movie.data.api.model.TmdbCreditList
import com.oldautumn.movie.data.api.model.TmdbMovieDetail
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieListModel
import com.oldautumn.movie.data.api.model.TraktMovieDetail

data class MovieDetailUiState(
    val movieDetail: TmdbMovieDetail? = null,
    val traktMovieDetail: TraktMovieDetail? = null,
    val movieCreditList: TmdbCreditList? = null,
    val recommendMovieList: TmdbSimpleMovieListModel? = null,
    val similarMovieList: TmdbSimpleMovieListModel? = null,
    val errorMessage: String? = null
)