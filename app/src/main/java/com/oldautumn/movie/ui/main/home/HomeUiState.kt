package com.oldautumn.movie.ui.main.home

import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieRevenueItem
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem

data class HomeUiState(
    val trendingMovieList: List<UnifyMovieTrendingItem>,
    val popularMovieList: List<MovieWithImage>,
    val revenueMovieList: List<UnifyMovieRevenueItem>,
    val errorMessage: String
)
