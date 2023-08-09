package com.oldautumn.movie.data.api.model

/*
*
{
      "character": "",
      "credit_id": "5967b06392514132e1005850",
      "release_date": "2019-12-31",
      "vote_count": 0,
      "video": false,
      "adult": false,
      "vote_average": 0,
      "title": "Untitled Manson Murders Project",
      "genre_ids": [
        80,
        18
      ],
      "original_language": "en",
      "original_title": "Untitled Manson Murders Project",
      "popularity": 1.1521,
      "id": 466272,
      "backdrop_path": null,
      "overview": "Plot unknown. The film will focus on the Manson murders that took place in California in 1969.",
      "poster_path": null
    }

* */
data class TmdbCastInMovie(
    val character: String,
    val credit_id: String,
    val release_date: String,
    val vote_count: Int,
    val video: Boolean,
    val adult: Boolean,
    val vote_average: Double,
    val title: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val popularity: Double,
    val id: Int,
    val backdrop_path: String?,
    val overview: String,
    val poster_path: String?,
)
