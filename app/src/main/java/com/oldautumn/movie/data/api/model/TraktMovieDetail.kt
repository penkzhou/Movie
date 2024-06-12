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
*
{
  "title": "TRON: Legacy",
  "year": 2010,
  "ids": {
    "trakt": 343,
    "slug": "tron-legacy-2010",
    "imdb": "tt1104001",
    "tmdb": 20526
  },
  "tagline": "The Game Has Changed.",
  "overview": "Sam Flynn, the tech-savvy and daring son of Kevin Flynn, investigates his father's disappearance and is pulled into The Grid. With the help of  a mysterious program named Quorra, Sam quests to stop evil dictator Clu from crossing into the real world.",
  "released": "2010-12-16",
  "runtime": 125,
  "country": "us",
  "updated_at": "2014-07-23T03:21:46.000Z",
  "trailer": null,
  "homepage": "http://disney.go.com/tron/",
  "status": "released",
  "rating": 8,
  "votes": 111,
  "comment_count": 92,
  "language": "en",
  "available_translations": [
    "en"
  ],
  "genres": [
    "action"
  ],
  "certification": "PG-13"
}
* */
data class TraktMovieDetail(
    val title: String?,
    val year: Int?,
    val ids: MovieIds,
    val tagline: String,
    val overview: String,
    val released: String,
    val runtime: Int,
    val country: String,
    val updated_at: String,
    val trailer: String?,
    val homepage: String?,
    val status: String,
    val rating: Double,
    val votes: Int,
    val comment_count: Int,
    val language: String,
    val available_translations: List<String>,
    val genres: List<String>,
    val certification: String?
)
