package com.oldautumn.movie.ui.movie

import com.oldautumn.movie.data.api.model.*

data class MovieDetailUiState(
    val movieDetail: TmdbMovieDetail? = null,
    val traktMovieDetail: TraktMovieDetail? = null,
    val movieCreditList: TmdbCreditList? = null,
    val movieAlbum: TmdbImageModel? = null,
    val recommendMovieList: TmdbSimpleItemListModel<TmdbSimpleMovieItem>? = null,
    val similarMovieList: TmdbSimpleItemListModel<TmdbSimpleMovieItem>? = null,
    val errorMessage: String? = null
)