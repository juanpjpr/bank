package com.example.bankchallenge.domain.common

sealed interface States {
    object Idle: States
    object Loading: States
    class Error(val resId: Int): States
}
