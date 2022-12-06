package com.oldautumn.movie.data.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthedInterceptor @Inject constructor(private val token: String, private val clientId: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("trakt-api-version", "2")
            .addHeader("trakt-api-key", "$clientId")
            .build()
        return chain.proceed(newRequest)
    }
}