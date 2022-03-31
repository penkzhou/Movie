package com.oldautumn.movie.di

import com.oldautumn.movie.data.api.AuthedInterceptor
import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Singleton
    @Provides
    @Named("provideOkHttpClient")
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Singleton
    @Provides
    @Named("tmdbApiService")
    fun provideTmdbApiService(): TmdbApiService {
        val logging = HttpLoggingInterceptor()

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(TmdbApiService::class.java)
    }


    @Singleton
    @Provides
    @Named("authedTraktApiService")
    fun provideAuthedTraktApiService(): TraktApiService {
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


    @Singleton
    @Provides
    @Named("traktApiService")
    fun provideTraktApiService(): TraktApiService {
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
}