package com.oldautumn.movie.ui.main.explore

import com.oldautumn.movie.data.api.model.TraktCollection

data class ExploreUiState(
    val popularCollectionList: List<TraktCollection>,
    val trendingCollectionList: List<TraktCollection>,
    val errorMessage: String,
)
