package com.oldautumn.movie.data.api.model

/*

{
  "rating": 9.38363,
  "votes": 51065,
  "distribution": {
    "1": 320,
    "2": 77,
    "3": 73,
    "4": 131,
    "5": 300,
    "6": 514,
    "7": 1560,
    "8": 4399,
    "9": 9648,
    "10": 34042
  }
}

* */
data class TraktRating(
    val rating: Double,
    val votes: Int,
    val distribution: Map<String, Int>,
)
