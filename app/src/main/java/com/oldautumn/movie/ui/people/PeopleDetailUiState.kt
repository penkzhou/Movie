package com.oldautumn.movie.ui.people

import com.oldautumn.movie.data.api.model.*

data class PeopleDetailUiState(
    val peopleDetail: TmdbPeople? = null,
    val traktPeopleDetail: TraktPeople? = null,
    val peopleMovieCast: MutableList<TmdbCombinedCast>? = mutableListOf(),
    val peopleMovieCrew: MutableList<TmdbCombinedCrew>? = mutableListOf(),
    val peopleTvCast: MutableList<TmdbCombinedCast>? = mutableListOf(),
    val peopleTvCrew: MutableList<TmdbCombinedCrew>? = mutableListOf(),
    val peopleImageList: MutableList<TmdbImageItem>? = mutableListOf(),
    val peopleImageSize: Int = 0,
    val errorMessage: String? = null
)