package com.oldautumn.movie.data.api.model

data class Movie(
    val title: String,
    val year: Int,
    val ids: MovieIds
)


/**
 * "trakt": 1,
 * "slug": "tron-legacy-2010",
 * "imdb": "tt1104001",
 * "tmdb": 20526
 */
data class MovieIds(
    val trakt: Int,
    val slug: String,
    val imdb: String,
    val tmdb: Int
)
