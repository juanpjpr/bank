package com.example.bankchallenge.domain.common

sealed interface ProcessState {
    object Idle: ProcessState
    object Loading: ProcessState
    class Error(val resId: Int): ProcessState
}
