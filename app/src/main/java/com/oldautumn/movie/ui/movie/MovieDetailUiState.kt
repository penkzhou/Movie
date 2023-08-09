package com.oldautumn.movie.ui.movie

import com.oldautumn.movie.data.api.model.MovieVideo
import com.oldautumn.movie.data.api.model.TmdbCreditList
import com.oldautumn.movie.data.api.model.TmdbImageModel
import com.oldautumn.movie.data.api.model.TmdbMovieDetail
import com.oldautumn.movie.data.api.model.TmdbSimpleItemListModel
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieItem
import com.oldautumn.movie.data.api.model.TraktMovieDetail

data class MovieDetailUiState(
    val movieDetail: TmdbMovieDetail? = null,
    val traktMovieDetail: TraktMovieDetail? = null,
    val movieCreditList: TmdbCreditList? = null,
    val movieAlbum: TmdbImageModel? = null,
    val recommendMovieList: TmdbSimpleItemListModel<TmdbSimpleMovieItem>? = null,
    val similarMovieList: TmdbSimpleItemListModel<TmdbSimpleMovieItem>? = null,
    val movieVideo: MovieVideo? = null,
    val errorMessage: String? = null,
)
