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
package com.oldautumn.movie.data.api

import com.oldautumn.movie.data.api.model.MovieVideo
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
import com.oldautumn.movie.data.api.model.TmdbTvRateModel
import com.oldautumn.movie.data.api.model.TvSeasonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("/3/movie/{movie_id}/images")
    suspend fun fetchMovieImageList(
        @Path("movie_id") movieId: Int,
    ): TmdbImageModel

    @GET("/3/tv/{tv_id}/images")
    suspend fun fetchTvImageList(
        @Path("tv_id") tvId: Int,
    ): TmdbImageModel

    @GET("/3/movie/{movie_id}?append_to_response=external_ids")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int,
    ): TmdbMovieDetail

    @GET("/3/movie/{movie_id}/videos")
    suspend fun fetchMovieVideo(
        @Path("movie_id") movieId: Int,
    ): MovieVideo

    @GET("/3/tv/{tv_id}?append_to_response=external_ids")
    suspend fun fetchTvDetail(
        @Path("tv_id") tvId: Int,
    ): TmdbTvDetail

    @GET("/3/tv/{tv_id}/season/{season_number}")
    suspend fun fetchTvSeasonDetail(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
    ): TvSeasonDetail

    @GET("/3/tv/{tv_id}/aggregate_credits")
    suspend fun fetchTvCredit(
        @Path("tv_id") tvId: Int,
    ): TmdbCreditList

    @GET("/3/movie/{movie_id}/credits")
    suspend fun fetchMovieCredit(
        @Path("movie_id") movieId: Int,
    ): TmdbCreditList

    @GET("/3/movie/{movie_id}/similar")
    suspend fun fetchSimilarMovieList(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
    ): TmdbSimpleItemListModel<TmdbSimpleMovieItem>

    @GET("/3/movie/{movie_id}/recommendations")
    suspend fun fetchRecommendMovieList(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
    ): TmdbSimpleItemListModel<TmdbSimpleMovieItem>

    @GET("/3/tv/{tv_id}/similar")
    suspend fun fetchSimilarTvList(
        @Path("tv_id") tvId: Int,
        @Query("page") page: Int = 1,
    ): TmdbSimpleItemListModel<TmdbSimpleTvItem>

    @GET("/3/tv/{tv_id}/recommendations")
    suspend fun fetchRecommendTvList(
        @Path("tv_id") movieId: Int,
        @Query("page") page: Int = 1,
    ): TmdbSimpleItemListModel<TmdbSimpleTvItem>

    @GET("/3/tv/{tv_id}/content_ratings")
    suspend fun fetchTmdbTvRatings(
        @Path("tv_id") tvId: Int,
    ): TmdbTvRateModel

    @GET("/3/person/{person_id}")
    suspend fun fetchTmdbPeopleDetail(
        @Path("person_id") personId: Int,
    ): TmdbPeople

    @GET("/3/person/{person_id}/combined_credits")
    suspend fun fetchTmdbPeopleCredit(
        @Path("person_id") personId: Int,
    ): TmdbCombinedCredit

    @GET("/3/person/{person_id}/images")
    suspend fun fetchTmdbPeopleImage(
        @Path("person_id") personId: Int,
    ): TmdbPeopleImage
}
