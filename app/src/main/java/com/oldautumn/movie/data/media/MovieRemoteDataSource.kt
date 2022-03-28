package com.oldautumn.movie.data.media

import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.Movie
import com.oldautumn.movie.data.api.model.MovieTrendingItem
import com.oldautumn.movie.data.api.model.ShowTrendingItem
import com.oldautumn.movie.data.api.model.TmdbImageModel


class MovieRemoteDataSource(
    private val traktApiService: TraktApiService,
    private val tmdbApiService: TmdbApiService,
) {
    suspend fun getTrendingMovieList(): List<MovieTrendingItem> =
        traktApiService.fetchTrendingMovieList()



    suspend fun getTrendingShowList(): List<ShowTrendingItem> =
        traktApiService.fetchTrendingShowList()


    suspend fun fetchPopularShowList(): List<Movie> =
        traktApiService.fetchPopularShowList()



    suspend fun fetchPopularMovieList(): List<Movie> =
        traktApiService.fetchPopularMovieList()


    suspend fun getMovieImage(movieId:Int): TmdbImageModel =
        tmdbApiService.fetchMovieImageList(movieId)


    suspend fun getTvImage(tvId:Int): TmdbImageModel =
        tmdbApiService.fetchTvImageList(tvId)


}