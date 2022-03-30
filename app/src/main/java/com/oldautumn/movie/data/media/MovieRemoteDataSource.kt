package com.oldautumn.movie.data.media

import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.*


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


    suspend fun getMovieImage(movieId: Int): TmdbImageModel =
        tmdbApiService.fetchMovieImageList(movieId)


    suspend fun getTvImage(tvId: Int): TmdbImageModel =
        tmdbApiService.fetchTvImageList(tvId)

    suspend fun getMovieDetail(movieId: Int): TmdbMovieDetail =
        tmdbApiService.fetchMovieDetail(movieId)

    suspend fun getTvDetail(tvId: Int): TmdbTvDetail =
        tmdbApiService.fetchTvDetail(tvId)

    suspend fun getMovieCast(movieId: Int): TmdbCreditList =
        tmdbApiService.fetchMovieCredit(movieId)

    suspend fun getTvCast(tvId: Int): TmdbCreditList =
        tmdbApiService.fetchTvCredit(tvId)


    suspend fun getTraktMovieDetail(movieSlug: String): TraktMovieDetail =
        traktApiService.fetchTraktMovieDetail(movieSlug)

    suspend fun getRecommendMovieList(movieId: Int): TmdbSimpleMovieListModel =
        tmdbApiService.fetchRecommendMovieList(movieId)

    suspend fun getSimilarMovieList(movieId: Int): TmdbSimpleMovieListModel =
        tmdbApiService.fetchSimilarMovieList(movieId)

    suspend fun getTraktReviewList(traktMovieId:String,sortType:String):List<TraktReview> =
        traktApiService.fetchTraktMovieReviewList(traktMovieId,sortType)

}