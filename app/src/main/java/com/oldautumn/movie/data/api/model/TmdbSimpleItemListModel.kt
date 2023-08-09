package com.oldautumn.movie.data.api.model

data class TmdbSimpleItemListModel<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int,
)
