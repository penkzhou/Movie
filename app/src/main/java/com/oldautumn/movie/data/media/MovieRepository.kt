package com.oldautumn.movie.data.media

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.*
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val remoteDataSource: MovieRemoteDataSource,
    private val loginRemoteDataSource: MovieRemoteDataSource,
    private val traktApiService: TraktApiService
) {

    suspend fun getTrendingMovieList(): List<UnifyMovieTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingMovieList()
        return movieTrendingList.map { it ->
            UnifyMovieTrendingItem(remoteDataSource.getMovieImage(it.movie.ids.tmdb), it)
        }

    }


    suspend fun getTrendingShowList(): List<UnifyTvTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingShowList()
        return movieTrendingList.map { it ->
            UnifyTvTrendingItem(remoteDataSource.getTvImage(it.show.ids.tmdb), it)
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


    suspend fun getMovieVideo(movieId: Int): MovieVideo {
        return remoteDataSource.getMovieVideo(movieId)
    }

    suspend fun getUserInfo(token:String): UserSettings {
        return loginRemoteDataSource.getUserSettings(token)
    }

    suspend fun getShowDetail(showId: Int): TmdbTvDetail {
        return remoteDataSource.getTvDetail(showId)
    }

    suspend fun getMovieCredits(movieId: Int): TmdbCreditList {
        return remoteDataSource.getMovieCast(movieId)
    }



    suspend fun getMovieAlbum(movieId: Int): TmdbImageModel {
        return remoteDataSource.getMovieImage(movieId)
    }

    suspend fun getShowCredits(showId: Int): TmdbCreditList {
        return remoteDataSource.getTvCast(showId)
    }


    suspend fun getTraktMovieDetail(movieSlug: String): TraktMovieDetail {
        return remoteDataSource.getTraktMovieDetail(movieSlug)
    }

    suspend fun getRecommendMovieList(movieId: Int): TmdbSimpleItemListModel<TmdbSimpleMovieItem> {
        return remoteDataSource.getRecommendMovieList(movieId)
    }

    suspend fun getSimilarMovieList(movieId: Int): TmdbSimpleItemListModel<TmdbSimpleMovieItem> {
        return remoteDataSource.getSimilarMovieList(movieId)
    }


    suspend fun getRecommendTvList(tvId: Int): TmdbSimpleItemListModel<TmdbSimpleTvItem> {
        return remoteDataSource.getRecommendTvList(tvId)
    }

    suspend fun getSimilarTvList(tvId: Int): TmdbSimpleItemListModel<TmdbSimpleTvItem> {
        return remoteDataSource.getSimilarTvList(tvId)
    }


    suspend fun getTraktTvRating(tvId: String): TraktRating {
        return remoteDataSource.getTraktShowRate(tvId)
    }


    suspend fun getTraktTvDetail(tvId: String): TraktShowDetail {
        return remoteDataSource.getTraktTvDetail(tvId)
    }

    suspend fun getTraktReviewList(traktMovieId: String, sortType: String): List<TraktReview> {
        return remoteDataSource.getTraktReviewList(traktMovieId, sortType)
    }

    suspend fun getTraktBoxOffice(): List<UnifyMovieRevenueItem> {
        val revenueList = remoteDataSource.getTraktMovieBoxOffice()
        return revenueList.map { it ->
            UnifyMovieRevenueItem(remoteDataSource.getMovieImage(it.movie.ids.tmdb), it)
        }
    }

    suspend fun getTraktPopularCollection(): List<TraktCollection> {
        return remoteDataSource.getTraktPopularCollection()
    }

    suspend fun getPeopleDetail(peopleId: Int): TmdbPeople {
        return remoteDataSource.getPeopleDetail(peopleId)
    }


    suspend fun getPeopleCredit(peopleId: Int): TmdbCombinedCredit {
        return remoteDataSource.getPeopleCredit(peopleId)
    }

    suspend fun getPeopleImage(peopleId: Int): TmdbPeopleImage {
        return remoteDataSource.getPeopleImage(peopleId)
    }


    fun getTraktReviewPageList(
        traktMovieId: String,
        sortType: String
    ): Flow<PagingData<TraktReview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TraktReviewPagingSource(
                    traktApiService,
                    traktMovieId,
                    sortType
                )
            }
        ).flow
    }

}