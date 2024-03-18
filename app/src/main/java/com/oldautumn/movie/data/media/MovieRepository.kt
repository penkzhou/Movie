/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oldautumn.movie.data.media

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.MediaWithImage
import com.oldautumn.movie.data.api.model.ModelWithImage
import com.oldautumn.movie.data.api.model.MovieVideo
import com.oldautumn.movie.data.api.model.ShowPlayedItem
import com.oldautumn.movie.data.api.model.ShowRecommendItem
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
import com.oldautumn.movie.data.api.model.TvSeasonDetail
import com.oldautumn.movie.data.api.model.UnifyMovieRevenueItem
import com.oldautumn.movie.data.api.model.UnifyMovieTrendingItem
import com.oldautumn.movie.data.api.model.UnifyTvTrendingItem
import com.oldautumn.movie.data.api.model.UserSettings
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val remoteDataSource: MovieRemoteDataSource,
    private val loginRemoteDataSource: MovieRemoteDataSource,
    private val traktApiService: TraktApiService
) {
    suspend fun getTrendingMovieList(): List<UnifyMovieTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingMovieList()
        return movieTrendingList.map {
            UnifyMovieTrendingItem(remoteDataSource.getMovieImage(it.movie.ids.tmdb), it)
        }
    }

    suspend fun getTrendingShowList(): List<UnifyTvTrendingItem> {
        val movieTrendingList = remoteDataSource.getTrendingShowList()
        return movieTrendingList.map {
            UnifyTvTrendingItem(remoteDataSource.getTvImage(it.show.ids.tmdb), it)
        }
    }

    suspend fun getMostRecommendShowList(period: String): List<ModelWithImage<ShowRecommendItem>> {
        val mostRecommendShowList = remoteDataSource.getMostRecommendShowList(period)
        return mostRecommendShowList.map {
            ModelWithImage(remoteDataSource.getTvImage(it.show.ids.tmdb), it)
        }
    }

    suspend fun getMostPlayedShowList(period: String): List<ModelWithImage<ShowPlayedItem>> {
        val mostPlayedShowList = remoteDataSource.getMostPlayedShowList(period)
        return mostPlayedShowList.map {
            ModelWithImage(remoteDataSource.getTvImage(it.show.ids.tmdb), it)
        }
    }

    suspend fun getPopularMovieList(): List<MediaWithImage> {
        val movieTrendingList = remoteDataSource.fetchPopularMovieList()
        return movieTrendingList.map {
            MediaWithImage(it, remoteDataSource.getMovieImage(it.ids.tmdb))
        }
    }

    suspend fun getPopularShowList(): List<MediaWithImage> {
        val movieTrendingList = remoteDataSource.fetchPopularShowList()
        return movieTrendingList.map {
            MediaWithImage(it, remoteDataSource.getTvImage(it.ids.tmdb))
        }
    }

    suspend fun getMovieDetail(movieId: Int): TmdbMovieDetail {
        return remoteDataSource.getMovieDetail(movieId)
    }

    suspend fun getMovieVideo(movieId: Int): MovieVideo {
        return remoteDataSource.getMovieVideo(movieId)
    }

    suspend fun getUserInfo(token: String): UserSettings {
        return loginRemoteDataSource.getUserSettings(token)
    }

    suspend fun getShowDetail(showId: Int): TmdbTvDetail {
        return remoteDataSource.getTvDetail(showId)
    }

    suspend fun getShowSeasonDetail(showId: Int, seasonNumber: Int): TvSeasonDetail {
        return remoteDataSource.getTvSeasonDetail(showId, seasonNumber)
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
        return revenueList.map {
            UnifyMovieRevenueItem(remoteDataSource.getMovieImage(it.movie.ids.tmdb), it)
        }
    }

    suspend fun getTraktPopularCollection(): List<TraktCollection> {
        return remoteDataSource.getTraktPopularCollection()
    }

    suspend fun getTraktTrendingCollection(): List<TraktCollection> {
        return remoteDataSource.getTraktTrendingCollection()
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
            config =
            PagingConfig(
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
