package com.oldautumn.movie.data.api

import com.oldautumn.movie.data.api.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbApiService {


    @GET("/3/movie/{movie_id}/images")
    suspend fun fetchMovieImageList(
        @Path("movie_id") movieId: Int
    ): TmdbImageModel


    @GET("/3/tv/{tv_id}/images")
    suspend fun fetchTvImageList(
        @Path("tv_id") tvId: Int
    ): TmdbImageModel


    @GET("/3/movie/{movie_id}?append_to_response=external_ids")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int
    ): TmdbMovieDetail


    @GET("/3/tv/{tv_id}?append_to_response=external_ids")
    suspend fun fetchTvDetail(
        @Path("tv_id") tvId: Int
    ): TmdbTvDetail


    @GET("/3/tv/{tv_id}/credits")
    suspend fun fetchTvCredit(
        @Path("tv_id") tvId: Int
    ): TmdbCreditList


    @GET("/3/movie/{movie_id}/credits")
    suspend fun fetchMovieCredit(
        @Path("movie_id") movieId: Int
    ): TmdbCreditList


    @GET("/3/movie/{movie_id}/similar")
    suspend fun fetchSimilarMovieList(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): TmdbSimpleItemListModel<TmdbSimpleMovieItem>


    @GET("/3/movie/{movie_id}/recommendations")
    suspend fun fetchRecommendMovieList(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): TmdbSimpleItemListModel<TmdbSimpleMovieItem>


    @GET("/3/tv/{tv_id}/similar")
    suspend fun fetchSimilarTvList(
        @Path("tv_id") tvId: Int,
        @Query("page") page: Int = 1
    ): TmdbSimpleItemListModel<TmdbSimpleTvItem>


    @GET("/3/tv/{tv_id}/recommendations")
    suspend fun fetchRecommendTvList(
        @Path("tv_id") movieId: Int,
        @Query("page") page: Int = 1
    ): TmdbSimpleItemListModel<TmdbSimpleTvItem>


    @GET("/3/tv/{tv_id}/content_ratings")
    suspend fun fetchTmdbTvRatings(
        @Path("tv_id") tvId: Int
    ): TmdbTvRateModel


}