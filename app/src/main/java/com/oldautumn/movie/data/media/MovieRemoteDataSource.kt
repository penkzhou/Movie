package com.oldautumn.movie.data.media

import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.MovieRevenueItem
import com.oldautumn.movie.data.api.model.MovieTrendingItem
import com.oldautumn.movie.data.api.model.MovieVideo
import com.oldautumn.movie.data.api.model.ShowPlayedItem
import com.oldautumn.movie.data.api.model.ShowRecommendItem
import com.oldautumn.movie.data.api.model.ShowTrendingItem
import com.oldautumn.movie.data.api.model.TmdbCombinedCredit
import com.oldautumn.movie.data.api.model.TmdbCreditList
import com.oldautumn.movie.data.api.model.TmdbImageModel
import com.oldautumn.movie.data.api.model.TmdbMovieDetail
import com.oldautumn.movie.data.api.model.TmdbPeople
import com.oldautumn.movie.data.api.model.TmdbPeopleImage
import com.oldautumn.movie.data.api.model.TmdbSimpleItemListModel
import com.oldautumn.movie.data.api.model.TmdbSimpleMovieItem
import com.oldautumn.movie.data.api.model.TmdbSimpleTvItem
import com.oldautumn.movie.data.api.model.TmdbTvDetail
import com.oldautumn.movie.data.api.model.TraktCollection
import com.oldautumn.movie.data.api.model.TraktMovieDetail
import com.oldautumn.movie.data.api.model.TraktRating
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.data.api.model.TraktShowDetail
import com.oldautumn.movie.data.api.model.TraktSimpleContentItem
import com.oldautumn.movie.data.api.model.TvSeasonDetail
import com.oldautumn.movie.data.api.model.UserSettings

class MovieRemoteDataSource(
    private val traktApiService: TraktApiService,
    private val tmdbApiService: TmdbApiService,
) {
    suspend fun getTrendingMovieList(): List<MovieTrendingItem> =
        traktApiService.fetchTrendingMovieList()

    suspend fun getTrendingShowList(): List<ShowTrendingItem> =
        traktApiService.fetchTrendingShowList()

    suspend fun getMostRecommendShowList(period: String): List<ShowRecommendItem> =
        traktApiService.fetchMostRecommendShowList(period)

    suspend fun getMostPlayedShowList(period: String): List<ShowPlayedItem> =
        traktApiService.fetchMostPlayedShowList(period)

    suspend fun fetchPopularShowList(): List<TraktSimpleContentItem> =
        traktApiService.fetchPopularShowList()

    suspend fun fetchPopularMovieList(): List<TraktSimpleContentItem> =
        traktApiService.fetchPopularMovieList()

    suspend fun getMovieImage(movieId: Int): TmdbImageModel =
        tmdbApiService.fetchMovieImageList(movieId)

    suspend fun getTvImage(tvId: Int): TmdbImageModel =
        tmdbApiService.fetchTvImageList(tvId)

    suspend fun getMovieDetail(movieId: Int): TmdbMovieDetail =
        tmdbApiService.fetchMovieDetail(movieId)

    suspend fun getMovieVideo(movieId: Int): MovieVideo =
        tmdbApiService.fetchMovieVideo(movieId)

    suspend fun getUserSettings(token: String): UserSettings =
        traktApiService.fetchUserInfo(mapOf(Pair("Authorization", "Bearer $token")))

    suspend fun getTvDetail(tvId: Int): TmdbTvDetail =
        tmdbApiService.fetchTvDetail(tvId)

    suspend fun getTvSeasonDetail(tvId: Int, seasonNumber: Int): TvSeasonDetail =
        tmdbApiService.fetchTvSeasonDetail(tvId, seasonNumber)

    suspend fun getMovieCast(movieId: Int): TmdbCreditList =
        tmdbApiService.fetchMovieCredit(movieId)

    suspend fun getTvCast(tvId: Int): TmdbCreditList =
        tmdbApiService.fetchTvCredit(tvId)

    suspend fun getTraktMovieDetail(movieSlug: String): TraktMovieDetail =
        traktApiService.fetchTraktMovieDetail(movieSlug)

    suspend fun getTraktTvDetail(movieSlug: String): TraktShowDetail =
        traktApiService.fetchTraktTvDetail(movieSlug)

    suspend fun getTraktShowRate(movieSlug: String): TraktRating =
        traktApiService.fetchTraktTvRate(movieSlug)

    suspend fun getRecommendMovieList(movieId: Int): TmdbSimpleItemListModel<TmdbSimpleMovieItem> =
        tmdbApiService.fetchRecommendMovieList(movieId)

    suspend fun getSimilarMovieList(movieId: Int): TmdbSimpleItemListModel<TmdbSimpleMovieItem> =
        tmdbApiService.fetchSimilarMovieList(movieId)

    suspend fun getRecommendTvList(tvId: Int): TmdbSimpleItemListModel<TmdbSimpleTvItem> =
        tmdbApiService.fetchRecommendTvList(tvId)

    suspend fun getSimilarTvList(tvId: Int): TmdbSimpleItemListModel<TmdbSimpleTvItem> =
        tmdbApiService.fetchSimilarTvList(tvId)

    suspend fun getTraktReviewList(traktMovieId: String, sortType: String): List<TraktReview> =
        traktApiService.fetchTraktMovieReviewList(traktMovieId, sortType, 1)

    suspend fun getTraktMovieBoxOffice(): List<MovieRevenueItem> =
        traktApiService.fetchWeeklyBoxOffice()

    suspend fun getTraktPopularCollection(): List<TraktCollection> =
        traktApiService.fetchTraktPopularCollection()

    suspend fun getPeopleDetail(peopleId: Int): TmdbPeople =
        tmdbApiService.fetchTmdbPeopleDetail(peopleId)

    suspend fun getPeopleCredit(peopleId: Int): TmdbCombinedCredit =
        tmdbApiService.fetchTmdbPeopleCredit(peopleId)

    suspend fun getPeopleImage(peopleId: Int): TmdbPeopleImage =
        tmdbApiService.fetchTmdbPeopleImage(peopleId)
}
