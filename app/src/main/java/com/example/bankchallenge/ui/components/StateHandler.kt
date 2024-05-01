package com.example.bankchallenge.ui.components

import androidx.compose.runtime.Composable
import com.example.bankchallenge.domain.common.ProcessState

@Composable
fun StateHandler(processState: ProcessState, onDismissError: () -> Unit) {
    when (processState) {
        is ProcessState.Error -> ModalError(onDismissError = { onDismissError() }, errorId = processState.resId)

        ProcessState.Idle -> Unit
        ProcessState.Loading -> LoadingOverlay()
    }
}