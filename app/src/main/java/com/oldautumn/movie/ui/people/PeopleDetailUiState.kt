package com.oldautumn.movie.ui.people

import com.oldautumn.movie.data.api.model.*

data class PeopleDetailUiState(
    val peopleDetail: TmdbPeople? = null,
    val traktPeopleDetail: TraktPeople? = null,
    val peopleMovieCast: MutableList<TmdbCombinedCast>,
    val peopleMovieCrew: MutableList<TmdbCombinedCrew>,
    val peopleTvCast: MutableList<TmdbCombinedCast>,
    val peopleTvCrew: MutableList<TmdbCombinedCrew>,
    val errorMessage: String? = null
)