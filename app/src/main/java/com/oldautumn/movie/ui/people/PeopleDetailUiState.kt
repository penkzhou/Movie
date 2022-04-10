package com.oldautumn.movie.ui.people

import com.oldautumn.movie.data.api.model.TmdbCombinedCast
import com.oldautumn.movie.data.api.model.TmdbCombinedCrew
import com.oldautumn.movie.data.api.model.TmdbPeople
import com.oldautumn.movie.data.api.model.TraktPeople

data class PeopleDetailUiState(
    val peopleDetail: TmdbPeople? = null,
    val traktPeopleDetail: TraktPeople? = null,
    val peopleMovieCast: MutableList<TmdbCombinedCast>? = mutableListOf(),
    val peopleMovieCrew: MutableList<TmdbCombinedCrew>? = mutableListOf(),
    val peopleTvCast: MutableList<TmdbCombinedCast>? = mutableListOf(),
    val peopleTvCrew: MutableList<TmdbCombinedCrew>? = mutableListOf(),
    val errorMessage: String? = null
)