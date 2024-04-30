package com.example.bankchallenge.ui.screens.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bankchallenge.R

@Composable
fun SuccessScreen(nextScreen: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieSuccess(Modifier.size(200.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { nextScreen() }) {
                Text("Siguiente")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSuccessScreen() {
    SuccessScreen {}
}

@Composable
fun LottieSuccess(
    modifier: Modifier,
) {
    val composition =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_success))
    LottieAnimation(
        modifier = modifier,
        composition = composition.value,
    )
}
