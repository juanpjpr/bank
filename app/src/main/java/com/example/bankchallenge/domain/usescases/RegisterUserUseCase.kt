package com.example.bankchallenge.domain.usescases

import com.example.bankchallenge.R
import com.example.bankchallenge.data.repository.AuthRepository
import com.example.bankchallenge.data.repository.UsersRepository
import com.example.bankchallenge.domain.common.Result
import com.example.bankchallenge.domain.model.NewUser
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) {

    suspend fun register(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Result<Unit> {
        authRepository.registerEmailAndPass(email, password)
        val userId = authRepository.getUserId()
        return if (userId != null) {
            usersRepository.registerData(NewUser(userId, name, surname))
        } else {
            Result.Error(R.string.register_firebase_error_id)
        }
    }
}