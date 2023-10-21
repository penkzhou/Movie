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
package com.oldautumn.movie.data.api.model

/*
{
      "id": 318846,
      "department": "Production",
      "original_language": "en",
      "original_title": "The Big Short",
      "job": "Producer",
      "overview": "The men who made millions from a global economic meltdown.",
      "genre_ids": [
        35,
        18
      ],
      "video": false,
      "media_type": "movie",
      "credit_id": "568349e09251414f6300f7b7",
      "poster_path": "/p11Ftd4VposrAzthkhF53ifYZRl.jpg",
      "popularity": 4.832312,
      "backdrop_path": "/jmlMLYEsYY1kRc5qHIyTdxCeVmZ.jpg",
      "vote_count": 2357,
      "title": "The Big Short",
      "adult": false,
      "vote_average": 7.3,
      "release_date": "2015-12-11"
    }
* */
data class TmdbCombinedCrew(
    val id: Int,
    val department: String,
    val original_language: String,
    val original_title: String,
    val job: String,
    val overview: String,
    val genre_ids: List<Int>,
    val video: Boolean,
    val media_type: String,
    val credit_id: String,
    val poster_path: String?,
    val popularity: Double,
    val backdrop_path: String,
    val vote_count: Int,
    val title: String,
    val adult: Boolean,
    val vote_average: Double,
    val release_date: String,
)
