/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oldautumn.movie.di

import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideTmdbApiService(
        @Named("tmdbRetrofit") retrofit: Retrofit,
    ): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("authedTraktApiService")
    fun provideAuthedTraktApiService(
        @Named("authedTraktRetrofit") retrofit: Retrofit,
    ): TraktApiService {
        return retrofit.create(TraktApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("loginAuthedTraktApiService")
    fun provideLoginAuthedTraktApiService(
        @Named("loginAuthedTraktRetrofit") retrofit: Retrofit,
    ): TraktApiService {
        return retrofit.create(TraktApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("traktApiService")
    fun provideTraktApiService(
        @Named("traktRetrofit") retrofit: Retrofit,
    ): TraktApiService {
        return retrofit.create(TraktApiService::class.java)
    }
}
