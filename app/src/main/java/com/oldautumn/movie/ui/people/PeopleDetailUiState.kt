/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    val errorMessage: String? = null
)
