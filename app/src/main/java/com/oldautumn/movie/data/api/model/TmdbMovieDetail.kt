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
* {
  "adult": false,
  "backdrop_path": "/zK7wNuUQ4w0QQp9FF20YTPVgpyN.jpg",
  "belongs_to_collection": {
    "id": 63043,
    "name": "TRON Collection",
    "poster_path": "/cvFlDIfOwhYe4ouAAfdyq9E5zlZ.jpg",
    "backdrop_path": "/o8NBfffQPE9tHTR9l7FuWbdVPHu.jpg"
  },
  "budget": 170000000,
  "genres": [
    {
      "id": 12,
      "name": "Adventure"
    },
    {
      "id": 28,
      "name": "Action"
    },
    {
      "id": 878,
      "name": "Science Fiction"
    }
  ],
  "homepage": "http://disney.go.com/tron/",
  "id": 20526,
  "imdb_id": "tt1104001",
  "original_language": "en",
  "original_title": "TRON: Legacy",
  "overview": "Sam Flynn, the tech-savvy and daring son of Kevin Flynn, investigates his father's disappearance and is pulled into The Grid. With the help of a mysterious program named Quorra, Sam quests to stop evil dictator Clu from crossing into the real world.",
  "popularity": 89.363,
  "poster_path": "/vuifSABRpSnxCAOxEnWpNbZSXpp.jpg",
  "production_companies": [
    {
      "id": 76043,
      "logo_path": "/9RO2vbQ67otPrBLXCaC8UMp3Qat.png",
      "name": "Revolution Sun Studios",
      "origin_country": "US"
    },
    {
      "id": 2,
      "logo_path": "/wdrCwmRnLFJhEoH8GSfymY85KHT.png",
      "name": "Walt Disney Pictures",
      "origin_country": "US"
    },
    {
      "id": 7161,
      "logo_path": null,
      "name": "LivePlanet",
      "origin_country": ""
    },
    {
      "id": 18713,
      "logo_path": null,
      "name": "Prana Studios",
      "origin_country": ""
    },
    {
      "id": 23791,
      "logo_path": null,
      "name": "Sean Bailey Productions",
      "origin_country": ""
    },
    {
      "id": 76067,
      "logo_path": null,
      "name": "Kontsept Film Company",
      "origin_country": ""
    }
  ],
  "production_countries": [
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2010-12-14",
  "revenue": 400062763,
  "runtime": 125,
  "spoken_languages": [
    {
      "english_name": "English",
      "iso_639_1": "en",
      "name": "English"
    }
  ],
  "status": "Released",
  "tagline": "The Game Has Changed.",
  "title": "TRON: Legacy",
  "video": false,
  "vote_average": 6.4,
  "vote_count": 5944
}
* */
data class TmdbMovieDetail(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Collection?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String?,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<Company>,
    val production_countries: List<Country>,
    val release_date: String?,
    val revenue: Long,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double,
    val vote_count: Int,
    val external_ids: ExternalIds
)

data class ExternalIds(
    val imdb_id: String?,
    val tvdb_id: Int?,
    val tvrage_id: Int?,
    val facebook_id: String?,
    val instagram_id: String?,
    val twitter_id: String?
)

data class Collection(
    val id: Int,
    val name: String,
    val poster_path: String,
    val backdrop_path: String
)
