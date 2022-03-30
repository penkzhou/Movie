package com.oldautumn.movie.data.media

import com.oldautumn.movie.data.api.model.*

class MovieRepository(
    private val remoteDataSource: MovieRemoteDataSource
) {

    suspend fun getTrendingMovieList(): List<UnifyMovieTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingMovieList()
        return movieTrendingList.map { it ->
            UnifyMovieTrendingItem(remoteDataSource.getMovieImage(it.movie.ids.tmdb), it)
        }

    }


    suspend fun getTrendingShowList(): List<UnifyShowTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingShowList()
        return movieTrendingList.map { it ->
            UnifyShowTrendingItem(remoteDataSource.getTvImage(it.show.ids.tmdb), it)
        }

    }


    suspend fun getPopularMovieList(): List<MovieWithImage> {
        val movieTrendingList = remoteDataSource.fetchPopularMovieList()
        return movieTrendingList.map { it ->
            MovieWithImage(it, remoteDataSource.getMovieImage(it.ids.tmdb))
        }

    }


    suspend fun getPopularShowList(): List<MovieWithImage> {
        val movieTrendingList = remoteDataSource.fetchPopularShowList()
        return movieTrendingList.map { it ->
            MovieWithImage(it, remoteDataSource.getTvImage(it.ids.tmdb))
        }

    }

    suspend fun getMovieDetail(movieId: Int): TmdbMovieDetail {
        return remoteDataSource.getMovieDetail(movieId)
    }

    suspend fun getShowDetail(showId: Int): TmdbTvDetail {
        return remoteDataSource.getTvDetail(showId)
    }

    suspend fun getMovieCredits(movieId: Int): TmdbCreditList {
        return remoteDataSource.getMovieCast(movieId)
    }

    suspend fun getShowCredits(showId: Int): TmdbCreditList {
        return remoteDataSource.getTvCast(showId)
    }


    suspend fun getTraktMovieDetail(movieSlug: String): TraktMovieDetail {
        return remoteDataSource.getTraktMovieDetail(movieSlug)
    }

    suspend fun getRecommendMovieList(movieId: Int): TmdbSimpleMovieListModel {
        return remoteDataSource.getRecommendMovieList(movieId)
    }

    suspend fun getSimilarMovieList(movieId: Int): TmdbSimpleMovieListModel {
        return remoteDataSource.getSimilarMovieList(movieId)
    }

}