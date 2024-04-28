package com.example.bankchallenge.ui.screens.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Balance: $1234", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Movimientos:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TransactionList(movements = movements, onTransactionClick = { })
    }
}

@Composable
fun TransactionList(
    movements: List<Movement>,
    onTransactionClick: (Movement) -> Unit
) {
    LazyColumn {
        itemsIndexed(movements) { index, transaction ->
            TransactionItem(movement = transaction, onTransactionClick = onTransactionClick)
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
        modifier = Modifier.padding(vertical = 8.dp)
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

data class Movement(
    val id: String,
    val description: String,
    val amount: Double
)

val movements = listOf(
    Movement(id = "1", description = "Compra en el supermercado", amount = -25.0),
    Movement(id = "2", description = "Depósito de salario", amount = 1500.0),
    Movement(id = "3", description = "Pago de factura de luz", amount = -50.0),
    Movement(id = "4", description = "Retiro de cajero automático", amount = -100.0),
    Movement(id = "5", description = "Transferencia a cuenta de ahorros", amount = -200.0)
)

