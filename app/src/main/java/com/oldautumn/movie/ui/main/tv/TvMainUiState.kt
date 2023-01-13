package com.oldautumn.movie.ui.main.tv

import com.oldautumn.movie.data.api.model.MediaWithImage
import com.oldautumn.movie.data.api.model.ModelWithImage
import com.oldautumn.movie.data.api.model.ShowPlayedItem
import com.oldautumn.movie.data.api.model.ShowRecommendItem
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem

data class TvMainUiState(
    val trendingShowList: List<UnifyTvTrendingItem>,
    val popularShowList: List<MediaWithImage>,
    val mostRecommendShowList: List<ModelWithImage<ShowRecommendItem>>,
    val mostPlayedShowList: List<ModelWithImage<ShowPlayedItem>>,
    val errorMessage: String
)
