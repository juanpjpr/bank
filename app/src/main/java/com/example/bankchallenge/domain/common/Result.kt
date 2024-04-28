package com.example.bankchallenge.domain.common

sealed class Result<T>(
    val data: T? = null,
    val errorMessage: Int? = null,
) {
    class Success<T>(data: T?) : Result<T>(data = data)

    class Error<T>(errorMessage: Int, data: T? = null) :
        Result<T>(data = data, errorMessage = errorMessage)
}