package com.oldautumn.movie.data.api

import com.oldautumn.movie.data.api.model.*
import retrofit2.http.*

interface TraktApiService {

    @FormUrlEncoded
    @POST("/oauth/device/code")
    suspend fun fetchDeviceCode(@Field("client_id") client_id: String): DeviceCode


    @FormUrlEncoded
    @POST("/oauth/device/token")
    suspend fun fetchAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): DeviceToken

    @GET("/movies/trending")
    suspend fun fetchTrendingMovieList(): List<MovieTrendingItem>


    @GET("/shows/trending")
    suspend fun fetchTrendingShowList(): List<ShowTrendingItem>


    @GET("/movies/watched/weekly")
    suspend fun fetchWeeklyWatchedMovieList(): List<MovieTrendingItem>


    @GET("/movies/popular")
    suspend fun fetchPopularMovieList(): List<Movie>


    @GET("/shows/popular")
    suspend fun fetchPopularShowList(): List<Movie>


    @GET("/movies/{movie_id}?extended=full")
    suspend fun fetchTraktMovieDetail(@Path("movie_id") movieId: String): TraktMovieDetail


    @GET("movies/{movie_id}/comments/{sort_type}")
    suspend fun fetchTraktMovieReviewList(
        @Path("movie_id") movieId: String,
        @Query("page") page: Int,
        @Path("sort_type") sortType: String,
        @Query("limit") limit: Int = 10
    ): List<TraktReview>


}