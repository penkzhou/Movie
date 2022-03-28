package com.oldautumn.movie.ui.main.home

import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem

data class HomeUiState(
    val trendingList: List<UnifyMovieTrendingItem>,
    val popularList: List<MovieWithImage>,
    val errorMessage: String
)
