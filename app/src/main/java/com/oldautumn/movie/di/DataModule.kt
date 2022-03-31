package com.oldautumn.movie.di

import com.oldautumn.movie.data.api.AuthedInterceptor
import com.oldautumn.movie.data.api.TmdbApiService
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.media.MovieRemoteDataSource
import com.oldautumn.movie.data.media.MovieRepository
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


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    @Named("normalOkhttpClient")
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Singleton
    @Provides
    @Named("authedOkHttpClient")
    fun provideAuthedOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val authedHeader = AuthedInterceptor(
            "",
            "759304793d0a51c6f3164c9e3cc6bebd22402bb0f6442a0bf22cc196e1759b08"
        )
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authedHeader)
            .build()
    }



    @Singleton
    @Provides
    @Named("traktRetrofit")
    fun provideTraktRetrofit(@Named("normalOkhttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.trakt.tv")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    @Named("authedTraktRetrofit")
    fun provideAuthedTraktRetrofit(@Named("authedOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.trakt.tv")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    @Named("tmdbRetrofit")
    fun provideTmdbRetrofit(@Named("normalOkhttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

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