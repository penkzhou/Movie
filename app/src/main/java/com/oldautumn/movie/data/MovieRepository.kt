package com.oldautumn.movie.data

import com.oldautumn.movie.data.api.model.MovieWithImage
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem

class MovieRepository(
    private val remoteDataSource: MovieRemoteDataSource
) {

    suspend fun getTrendingMovieList(): List<UnifyMovieTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingMovieList()
        return movieTrendingList.map { it ->
            UnifyMovieTrendingItem(remoteDataSource.getMovieImage(it.movie.ids.tmdb), it)
        }

    }


    suspend fun getPopularMovieList(): List<MovieWithImage> {
        val movieTrendingList = remoteDataSource.fetchPopularMovieList()
        return movieTrendingList.map { it ->
            MovieWithImage(it, remoteDataSource.getMovieImage(it.ids.tmdb))
        }

    }

}