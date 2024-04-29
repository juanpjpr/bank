package com.example.bankchallenge.domain.model

data class NewUser(
    val email: String,
    val password: String,
    val name: String,
    val lastName: String,
    val byteArray: ByteArray
)
