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


    @GET("/users/settings")
    suspend fun fetchUserInfo(@HeaderMap headers:Map<String, String> ): UserSettings


    @GET("/shows/trending")
    suspend fun fetchTrendingShowList(): List<ShowTrendingItem>


    @GET("/movies/watched/weekly")
    suspend fun fetchWeeklyWatchedMovieList(): List<MovieTrendingItem>


    @GET("movies/boxoffice")
    suspend fun fetchWeeklyBoxOffice(): List<MovieRevenueItem>


    @GET("/movies/popular")
    suspend fun fetchPopularMovieList(): List<TraktSimpleContentItem>


    @GET("/shows/popular")
    suspend fun fetchPopularShowList(): List<TraktSimpleContentItem>


    @GET("/movies/{movie_id}?extended=full")
    suspend fun fetchTraktMovieDetail(@Path("movie_id") movieId: String): TraktMovieDetail


    @GET("/shows/{tv_id}?extended=full")
    suspend fun fetchTraktTvDetail(@Path("tv_id") tvId: String): TraktShowDetail


    @GET("movies/{movie_id}/comments/{sort_type}")
    suspend fun fetchTraktMovieReviewList(
        @Path("movie_id") movieId: String,
        @Path("sort_type") sortType: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): List<TraktReview>


    @GET("shows/{tv_id}/comments/{sort_type}")
    suspend fun fetchTraktTvReviewList(
        @Path("tv_id") tvId: String,
        @Path("sort_type") sortType: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): List<TraktReview>


    @GET("shows/{tv_id}/ratings")
    suspend fun fetchTraktTvRate(
        @Path("tv_id") tvId: String
    ): TraktRating

    @GET("lists/popular")
    suspend fun fetchTraktPopularCollection(
    ): List<TraktCollection>


}