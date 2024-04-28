package com.example.bankchallenge.ui.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bankchallenge.R


@Composable
fun RegisterScreen(onRegistrationSuccess: () -> Unit) {
    val viewModel = hiltViewModel<RegisterViewModel>()
    val showPassword = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "",
            onValueChange = { },
            label = { Text("Name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.login_email),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = "",
            onValueChange = { },
            label = { Text("Surname") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.login_email),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = stringResource(R.string.login_email),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        viewModel.emailError.value?.let {
            Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
        }
        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.onPasswordChanged(it) },
            leadingIcon = {
                if (showPassword.value) {
                    IconButton(onClick = { showPassword.value = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = stringResource(id = R.string.login_hide_password)
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(id = R.string.login_show_password)
                        )
                    }
                }
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        )
        viewModel.passwordError.value?.let {
            Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
        }
        Button(onClick = { }) {
            Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = "foto")
            Text("Tomar foto")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(onRegistrationSuccess = {})
}