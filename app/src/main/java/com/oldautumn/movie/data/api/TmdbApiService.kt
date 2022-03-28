package com.oldautumn.movie.data.api

import com.oldautumn.movie.data.api.model.TmdbImageModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val defaultAppKey = "1fb9e261bd10339f78c0737494452323"

interface TmdbApiService {


    @GET("/3/movie/{movie_id}/images")
    suspend fun fetchMovieImageList(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appKey: String = defaultAppKey
    ): TmdbImageModel


    @GET("/3/tv/{tv_id}/images")
    suspend fun fetchTvImageList(
        @Path("tv_id") tvId: Int,
        @Query("api_key") appKey: String = defaultAppKey
    ): TmdbImageModel
}