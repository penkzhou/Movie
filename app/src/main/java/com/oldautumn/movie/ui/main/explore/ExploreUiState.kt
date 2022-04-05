package com.oldautumn.movie.ui.main.explore

import com.oldautumn.movie.data.api.model.TraktCollection

data class ExploreUiState(
    val collectionList: List<TraktCollection>,
    val errorMessage: String,
)
