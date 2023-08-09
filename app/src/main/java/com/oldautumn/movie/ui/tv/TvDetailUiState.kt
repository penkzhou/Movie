package com.oldautumn.movie.ui.tv

import com.oldautumn.movie.data.api.model.TmdbCreditList
import com.oldautumn.movie.data.api.model.TmdbSimpleItemListModel
import com.oldautumn.movie.data.api.model.TmdbSimpleTvItem
import com.oldautumn.movie.data.api.model.TmdbTvDetail
import com.oldautumn.movie.data.api.model.TraktRating
import com.oldautumn.movie.data.api.model.TraktShowDetail

data class TvDetailUiState(
    val tvDetail: TmdbTvDetail? = null,
    val traktTvDetail: TraktShowDetail? = null,
    val tvCreditList: TmdbCreditList? = null,
    val traktRating: TraktRating? = null,
    val recommendTvList: TmdbSimpleItemListModel<TmdbSimpleTvItem>? = null,
    val similarTvList: TmdbSimpleItemListModel<TmdbSimpleTvItem>? = null,
    val errorMessage: String? = null,
)
