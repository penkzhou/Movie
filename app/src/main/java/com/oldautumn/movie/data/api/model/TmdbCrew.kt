package com.oldautumn.movie.data.api.model

/*
*
{
      "adult": false,
      "gender": 2,
      "id": 9828,
      "known_for_department": "Acting",
      "name": "Garrett Hedlund",
      "original_name": "Garrett Hedlund",
      "popularity": 8.712,
      "profile_path": "/qe7hflOB1B9xA8s9truiYQAz8xw.jpg",
      "cast_id": 44,
      "character": "Sam Flynn",
      "credit_id": "52fe43ebc3a368484e005d19",
      "order": 0
}

* */
data class TmdbCrew(
    val adult: Boolean,
    val gender: Int?,
    val known_for_department: String,
    val id: Int,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?,
    val credit_id: String,
    val department: String,
    val job: String,
)
