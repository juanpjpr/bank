package com.example.bankchallenge.ui.screens.home.domain

import com.example.bankchallenge.domain.model.Movement

sealed interface ViewState {
    data object Home : ViewState
    class MovementDetail(val movement: Movement) : ViewState
}
