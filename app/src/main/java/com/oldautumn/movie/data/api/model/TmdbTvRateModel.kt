package com.oldautumn.movie.data.api.model

data class TmdbTvRateModel(
    val results: List<TmdbTvRateItem>,
    val id: Int
)

data class TmdbTvRateItem(
    val iso_3166_1: String,
    val rating: String
)
