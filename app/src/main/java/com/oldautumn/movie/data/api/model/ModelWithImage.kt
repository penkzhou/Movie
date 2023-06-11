package com.oldautumn.movie.data.api.model

data class ModelWithImage<T>(
    val image: TmdbImageModel,
    val show: T
)
