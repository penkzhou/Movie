package com.oldautumn.movie.data.api

import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.data.api.model.DeviceToken
import com.oldautumn.movie.data.api.model.MovieRevenueItem
import com.oldautumn.movie.data.api.model.MovieTrendingItem
import com.oldautumn.movie.data.api.model.ShowPlayedItem
import com.oldautumn.movie.data.api.model.ShowRecommendItem
import com.oldautumn.movie.data.api.model.ShowTrendingItem
import com.oldautumn.movie.data.api.model.TraktCollection
import com.oldautumn.movie.data.api.model.TraktMovieDetail
import com.oldautumn.movie.data.api.model.TraktRating
import com.oldautumn.movie.data.api.model.TraktReview
import com.oldautumn.movie.data.api.model.TraktShowDetail
import com.oldautumn.movie.data.api.model.TraktSimpleContentItem
import com.oldautumn.movie.data.api.model.UserSettings
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TraktApiService {

    @FormUrlEncoded
    @POST("/oauth/device/code")
    suspend fun fetchDeviceCode(@Field("client_id") client_id: String): DeviceCode

    @FormUrlEncoded
    @POST("/oauth/device/token")
    suspend fun fetchAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): DeviceToken

    @GET("/movies/trending")
    suspend fun fetchTrendingMovieList(): List<MovieTrendingItem>

    @GET("/users/settings")
    suspend fun fetchUserInfo(@HeaderMap headers: Map<String, String>): UserSettings

    @GET("/shows/trending")
    suspend fun fetchTrendingShowList(): List<ShowTrendingItem>

    @GET("/shows/recommended/{period}")
    suspend fun fetchMostRecommendShowList(@Path("period") period: String): List<ShowRecommendItem>

    @GET("/shows/played/{period}")
    suspend fun fetchMostPlayedShowList(@Path("period") period: String): List<ShowPlayedItem>

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
        @Query("limit") limit: Int = 10,
    ): List<TraktReview>

    @GET("shows/{tv_id}/comments/{sort_type}")
    suspend fun fetchTraktTvReviewList(
        @Path("tv_id") tvId: String,
        @Path("sort_type") sortType: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
    ): List<TraktReview>

    @GET("shows/{tv_id}/ratings")
    suspend fun fetchTraktTvRate(
        @Path("tv_id") tvId: String,
    ): TraktRating

    @GET("lists/popular")
    suspend fun fetchTraktPopularCollection(): List<TraktCollection>

    @GET("lists/trending")
    suspend fun fetchTraktTrendingCollection(): List<TraktCollection>

    @GET("lists/{list_id}")
    suspend fun fetchTraktCollectionDetail(): List<TraktCollection>

    @GET("lists/{list_id}/items/{type}")
    suspend fun fetchTraktCollectionListByType(): List<TraktCollection>
}
