package com.oldautumn.movie.ui.tv

import com.oldautumn.movie.data.api.model.TvSeasonDetail

data class TvSeasonDetailUiState(
    val tvSeasonDetail: TvSeasonDetail? = null,

    val errorMessage: String? = null,
)
