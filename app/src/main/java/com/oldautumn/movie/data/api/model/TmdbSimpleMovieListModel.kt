package com.oldautumn.movie.data.api.model

data class TmdbSimpleMovieListModel(
    val page: Int,
    val results: List<TmdbSimpleMovieItem>,
    val total_pages: Int,
    val total_results: Int
)
