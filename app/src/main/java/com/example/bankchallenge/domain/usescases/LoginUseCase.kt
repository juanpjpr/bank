package com.example.bankchallenge.domain.usescases

import com.example.bankchallenge.data.repository.AuthRepository
import com.example.bankchallenge.domain.common.Result
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun login(email: String, password: String): Result<Unit> {
        return authRepository.login(email, password)
    }
}