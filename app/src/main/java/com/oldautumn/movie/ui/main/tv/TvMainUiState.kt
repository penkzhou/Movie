package com.oldautumn.movie.ui.main.tv

import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem

data class TvMainUiState(
    val trendingShowList: List<UnifyTvTrendingItem>,
    val popularShowList: List<MovieWithImage>,
    val errorMessage: String
)
