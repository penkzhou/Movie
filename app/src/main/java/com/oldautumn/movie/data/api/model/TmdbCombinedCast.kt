package com.oldautumn.movie.data.api.model

/*
{
      "id": 54,
      "original_language": "en",
      "episode_count": 2,
      "overview": "Growing Pains is an American television sitcom about an affluent family, residing in Huntington, Long Island, New York, with a working mother and a stay-at-home psychiatrist father raising three children together, which aired on ABC from September 24, 1985, to April 25, 1992.",
      "origin_country": [
        "US"
      ],
      "original_name": "Growing Pains",
      "genre_ids": [
        35
      ],
      "name": "Growing Pains",
      "media_type": "tv",
      "poster_path": "/eKyeUFwjc0LhPSp129IHpXniJVR.jpg",
      "first_air_date": "1985-09-24",
      "vote_average": 6.2,
      "vote_count": 25,
      "character": "",
      "backdrop_path": "/xYpXcp7S8pStWihcksTQQue3jlV.jpg",
      "popularity": 2.883124,
      "credit_id": "525333fb19c295794002c720"
    }
* */
data class TmdbCombinedCast(
    val id: Int,
    val original_language: String,
    val episode_count: Int,
    val overview: String,
    val origin_country: List<String>,
    val original_title: String,
    val original_name: String,
    val genre_ids: List<Int>,
    val title: String,
    val name: String,
    val media_type: String,
    val poster_path: String?,
    val first_air_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val character: String,
    val backdrop_path: String,
    val popularity: Double,
    val credit_id: String
)
