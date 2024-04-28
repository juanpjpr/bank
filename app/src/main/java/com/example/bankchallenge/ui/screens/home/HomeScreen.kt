package com.example.bankchallenge.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankchallenge.domain.model.Movement
import com.example.bankchallenge.ui.screens.home.domain.ViewState

@Composable
fun HomeScreen(
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    when (val state = viewModel.view.value) {

        ViewState.Home -> HomeView(viewModel)
        is ViewState.MovementDetail -> MovementDetailView(
            viewModel = viewModel,
            movement = state.movement
        )
    }
}

@Composable
fun MovementDetailView(viewModel: HomeViewModel, movement: Movement) {
    BackHandler {
        viewModel.onBackFromDetail()
    }
    Scaffold(
        topBar = { Text(text = "Detalle de movimiento") }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Movements Detail")
            Text(text = "Description: ${movement.description}")
            Text(text = "Monto: ${movement.amount}")
        }
    }
}

@Composable
fun HomeView(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Balance: ${viewModel.balance.value}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Movimientos:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TransactionList(
            movements = viewModel.movements.value,
            onTransactionClick = { viewModel.onMovementClicked(it) })
    }
}


@Composable
fun TransactionList(
    movements: List<Movement>,
    onTransactionClick: (Movement) -> Unit
) {
    LazyColumn {
        itemsIndexed(movements) { index, transaction ->
            TransactionItem(movement = transaction, onTransactionClick = { onTransactionClick(it) })
            if (index != movements.size - 1) Divider()
        }
    }
}

@Composable
fun TransactionItem(
    movement: Movement,
    onTransactionClick: (Movement) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onTransactionClick(movement) }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = movement.description,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "$" + movement.amount,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}



