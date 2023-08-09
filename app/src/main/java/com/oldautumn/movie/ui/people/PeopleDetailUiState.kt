package com.oldautumn.movie.ui.people

import com.oldautumn.movie.data.api.model.TmdbCombinedCast
import com.oldautumn.movie.data.api.model.TmdbCombinedCrew
import com.oldautumn.movie.data.api.model.TmdbImageItem
import com.oldautumn.movie.data.api.model.TmdbPeople
import com.oldautumn.movie.data.api.model.TraktPeople

data class PeopleDetailUiState(
    val peopleDetail: TmdbPeople? = null,
    val traktPeopleDetail: TraktPeople? = null,
    val peopleMovieCast: List<TmdbCombinedCast>? = listOf(),
    val peopleMovieCrew: List<TmdbCombinedCrew>? = listOf(),
    val peopleTvCast: List<TmdbCombinedCast>? = listOf(),
    val peopleTvCrew: List<TmdbCombinedCrew>? = listOf(),
    val peopleImageList: List<TmdbImageItem>? = listOf(),
    val peopleImageSize: Int = 0,
    val errorMessage: String? = null,
)
