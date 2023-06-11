package com.oldautumn.movie.data.api.model

data class TmdbImageModel(
    val id: Int,
    val backdrops: List<TmdbImageItem>,
    val posters: List<TmdbImageItem>
)

data class TmdbImageItem(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val widtb: Int,
    val vote_average: Double,
    val vote_count: Int,
    val iso_639_1: String?
)
