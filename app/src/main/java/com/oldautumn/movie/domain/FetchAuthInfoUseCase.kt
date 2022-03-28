package com.oldautumn.movie.domain

import com.oldautumn.movie.data.AuthRepository
import kotlinx.coroutines.flow.Flow

class FetchAuthInfoUseCase(authRepository: AuthRepository) {


    private val authString: Flow<String> = authRepository.authString


    operator fun invoke(): Flow<String> {
        return authString
    }
}