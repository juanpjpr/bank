package com.example.bankchallenge.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bankchallenge.domain.model.Movement
import com.example.bankchallenge.ui.screens.home.domain.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _movements = mutableStateOf<List<Movement>>(emptyList())
    val movements: State<List<Movement>> = _movements

    private val _balance = mutableStateOf("")
    val balance: State<String> = _balance

    private val _view = mutableStateOf<ViewState>(ViewState.Home)
    val view: State<ViewState> = _view

    private val movementsFake = listOf(
        Movement(id = "1", description = "Compra en el supermercado", amount = -25.0),
        Movement(id = "2", description = "Depósito de salario", amount = 1500.0),
        Movement(id = "3", description = "Pago de factura de luz", amount = -50.0),
        Movement(id = "4", description = "Retiro de cajero automático", amount = -100.0),
        Movement(id = "5", description = "Transferencia a cuenta de ahorros", amount = -200.0)
    )

    init {
        _movements.value = movementsFake
    }

    fun onMovementClicked(movement: Movement) {
        _view.value = ViewState.MovementDetail(movement)
    }

    fun onBackFromDetail() {
        _view.value = ViewState.Home
    }
}

