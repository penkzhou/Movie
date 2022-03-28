package com.oldautumn.movie.ui.main.home

import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem
import com.oldautumn.movie.data.api.model.UnifyShowTrendingItem

data class HomeUiState(
    val trendingMovieList: List<UnifyMovieTrendingItem>,
    val popularMovieList: List<MovieWithImage>,
    val trendingShowList: List<UnifyShowTrendingItem>,
    val popularShowList: List<MovieWithImage>,
    val errorMessage: String
)
