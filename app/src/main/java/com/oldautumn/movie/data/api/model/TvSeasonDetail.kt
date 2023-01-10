package com.oldautumn.movie.data.api.model

data class TvSeasonDetail(
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>,
    val name: String,
    val overview: String,
    val id: Int,
    val poster_path: String,
    val season_number: Int
)
