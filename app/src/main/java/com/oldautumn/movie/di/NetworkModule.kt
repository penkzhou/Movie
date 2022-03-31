package com.oldautumn.movie.di

import com.oldautumn.movie.data.api.AuthedInterceptor
import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideTmdbApiService(
        @Named("tmdbRetrofit") retrofit: Retrofit
    ): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }


    @Singleton
    @Provides
    @Named("authedTraktApiService")
    fun provideAuthedTraktApiService(
        @Named("authedTraktRetrofit") retrofit: Retrofit
    ): TraktApiService {
        return retrofit.create(TraktApiService::class.java)
    }


    @Singleton
    @Provides
    @Named("traktApiService")
    fun provideTraktApiService(
        @Named("traktRetrofit") retrofit: Retrofit
    ): TraktApiService {
        return retrofit.create(TraktApiService::class.java)
    }
}