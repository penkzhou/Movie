package com.oldautumn.movie.di

import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Singleton
    @Provides
    @Named("movieRemoteDataSource")
    fun provideMovieRemoteDataSource(
        tmdbApiService: TmdbApiService,
        @Named("authedTraktApiService") traktApiService: TraktApiService,
    ): MovieRemoteDataSource {
        return MovieRemoteDataSource(traktApiService, tmdbApiService)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(@Named("movieRemoteDataSource") movieRemoteDataSource: MovieRemoteDataSource): MovieRepository {
        return MovieRepository(movieRemoteDataSource)
    }

}