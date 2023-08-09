package com.oldautumn.movie.data.api.model

/*

{
      "id": 76203,
      "department": "Production",
      "original_language": "en",
      "original_title": "12 Years a Slave",
      "job": "Producer",
      "overview": "In the pre-Civil War United States, Solomon Northup, a free black man from upstate New York, is abducted and sold into slavery. Facing cruelty as well as unexpected kindnesses Solomon struggles not only to stay alive, but to retain his dignity. In the twelfth year of his unforgettable odyssey, Solomon’s chance meeting with a Canadian abolitionist will forever alter his life.",
      "vote_count": 3284,
      "video": false,
      "poster_path": "/kb3X943WMIJYVg4SOAyK0pmWL5D.jpg",
      "backdrop_path": "/xnRPoFI7wzOYviw3PmoG94X2Lnc.jpg",
      "title": "12 Years a Slave",
      "popularity": 6.62674,
      "genre_ids": [
        18,
        36
      ],
      "vote_average": 7.9,
      "adult": false,
      "release_date": "2013-10-18",
      "credit_id": "52fe492cc3a368484e11dfe1"
    }

* */
data class TmdbCrewInMovie(
    val id: Int,
    val department: String,
    val original_language: String,
    val original_title: String,
    val job: String,
    val overview: String,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String,
    val backdrop_path: String,
    val title: String,
    val popularity: Double,
    val genre_ids: List<Int>,
    val vote_average: Double,
    val adult: Boolean,
    val release_date: String,
    val credit_id: String,
)
