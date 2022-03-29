package com.oldautumn.movie

object MovieUtils {
    fun getMoviePosterUrl(movieImagePath: String): String {
        //https://image.tmdb.org/t/p/w500/${movieWithImage.image.posters[0].file_path}"
        return "https://image.tmdb.org/t/p/w500$movieImagePath"
    }

    fun getMovieBackdropUrl(movieImagePath: String): String {
        //https://image.tmdb.org/t/p/w500/${movieWithImage.image.backdrops[0].file_path}"
        return "https://image.tmdb.org/t/p/w500$movieImagePath"
    }
}