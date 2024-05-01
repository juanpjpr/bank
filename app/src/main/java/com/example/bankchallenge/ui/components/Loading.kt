package com.example.bankchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingOverlay() = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.7f)),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    CircularProgressIndicator()
    Text(text = "Procesando", style = MaterialTheme.typography.titleMedium, color = Color.White)
}