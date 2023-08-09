package com.oldautumn.movie.data.api.model

data class TraktSimpleContentItem(
    val title: String,
    val year: Int,
    val ids: MovieIds,
)

data class MovieIds(
    val trakt: Int,
    val slug: String,
    val imdb: String,
    val tmdb: Int,
    val tvdb: Int,
)
