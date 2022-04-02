package com.oldautumn.movie.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiProvider {
    fun getApiService(): TraktApiService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.trakt.tv")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(TraktApiService::class.java)
    }

    fun getAuthedApiService(): TraktApiService {
        val logging = HttpLoggingInterceptor()
        val authedHeader = AuthedInterceptor(
            "",
            "759304793d0a51c6f3164c9e3cc6bebd22402bb0f6442a0bf22cc196e1759b08"
        )
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authedHeader)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.trakt.tv")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(TraktApiService::class.java)


    }

}