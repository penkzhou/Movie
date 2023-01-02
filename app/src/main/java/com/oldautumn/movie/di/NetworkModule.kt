package com.oldautumn.movie.di

import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit

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
    @Named("loginAuthedTraktApiService")
    fun provideLoginAuthedTraktApiService(
        @Named("loginAuthedTraktRetrofit") retrofit: Retrofit
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
