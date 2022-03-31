package com.oldautumn.movie.data.api

import com.oldautumn.movie.data.api.model.*
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbApiService {


    @GET("/3/movie/{movie_id}/images")
    suspend fun fetchMovieImageList(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323"
    ): TmdbImageModel


    @GET("/3/tv/{tv_id}/images")
    suspend fun fetchTvImageList(
        @Path("tv_id") tvId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323"
    ): TmdbImageModel


    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323"
    ): TmdbMovieDetail


    @GET("/3/tv/{tv_id}")
    suspend fun fetchTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323"
    ): TmdbTvDetail


    @GET("/3/tv/{tv_id}/credits")
    suspend fun fetchTvCredit(
        @Path("tv_id") tvId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323"
    ): TmdbCreditList


    @GET("/3/movie/{movie_id}/credits")
    suspend fun fetchMovieCredit(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323"
    ): TmdbCreditList


    @GET("/3/movie/{movie_id}/similar")
    suspend fun fetchSimilarMovieList(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323",
        @Query("page") page: Int = 1
    ): TmdbSimpleMovieListModel


    @GET("/3/movie/{movie_id}/recommendations")
    suspend fun fetchRecommendMovieList(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appKey: String = "1fb9e261bd10339f78c0737494452323",
        @Query("page") page: Int = 1
    ): TmdbSimpleMovieListModel


}