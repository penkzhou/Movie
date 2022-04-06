package com.oldautumn.movie.data.api.model

data class TmdbCombinedCredit(
    val id: Int,
    val cast: List<TmdbCombinedCast>,
    val crew: List<TmdbCombinedCrew>
)
