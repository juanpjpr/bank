package com.example.bankchallenge.data.model

data class Transaction(
    val id: String,
    val description: String,
    val amount: Double,
    val timestamp: Long
)