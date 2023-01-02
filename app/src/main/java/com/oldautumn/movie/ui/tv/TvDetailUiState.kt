package com.oldautumn.movie.ui.tv

import com.oldautumn.movie.data.api.model.*

data class TvDetailUiState(
    val tvDetail: TmdbTvDetail? = null,
    val traktTvDetail: TraktShowDetail? = null,
    val tvCreditList: TmdbCreditList? = null,
    val traktRating: TraktRating? = null,
    val recommendTvList: TmdbSimpleItemListModel<TmdbSimpleTvItem>? = null,
    val similarTvList: TmdbSimpleItemListModel<TmdbSimpleTvItem>? = null,
    val errorMessage: String? = null
)
