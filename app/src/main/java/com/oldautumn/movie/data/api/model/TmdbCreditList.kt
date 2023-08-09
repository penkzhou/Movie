package com.oldautumn.movie.data.api.model

data class TmdbCreditList(
    val cast: List<TmdbCast>,
    val crew: List<TmdbCrew>,
    val id: Int,
)
