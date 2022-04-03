package com.oldautumn.movie.ui.main.home

import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem

data class HomeUiState(
    val trendingMovieList: List<UnifyMovieTrendingItem>,
    val popularMovieList: List<MovieWithImage>,
    val errorMessage: String
)
