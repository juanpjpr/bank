package com.example.bankchallenge.ui.screens.registration

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.bankchallenge.R
import com.example.bankchallenge.ui.components.StateHandler


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
            value = viewModel.name.value,
            onValueChange = { viewModel.onNameChanged(it) },
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
        viewModel.nameError.value?.let {
            Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
        }
        TextField(
            value = viewModel.surname.value,
            onValueChange = { viewModel.onSurnameChanged(it) },
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
        viewModel.surnameError.value?.let {
            Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
        }
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
        PhotoPicker(
            availableUri = viewModel.availableUri.value,
            onUriLoaded = { viewModel.onUriLoaded(it) })
        viewModel.uriError.value?.let {
            Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
        }

        if (viewModel.uri.value != null) {
            Image(
                modifier = Modifier.size(144.dp),
                painter = rememberAsyncImagePainter(viewModel.uri.value),
                contentDescription = stringResource(id = R.string.register_photo_id),
            )
        }
        viewModel.failureRegister.value?.let {
            Text(text = stringResource(id = it), color = MaterialTheme.colorScheme.error)
        }


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.onRegisterClick(onRegistrationSuccess) }) {
            Text("Register")
        }
    }

    StateHandler(
        viewModel.registerUiProcessState.value,
        onDismissError = { viewModel.onErrorDismiss() })
}


@Composable
private fun PhotoPicker(
    availableUri: Uri,
    onUriLoaded: (Uri?) -> Unit
) {
    val context = LocalContext.current
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                onUriLoaded(availableUri)
            }
        }

    val launchCamera = { cameraLauncher.launch(availableUri) }
    val cameraPermissionRevokedText =
        stringResource(id = R.string.register_camera_revoke)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(context, cameraPermissionRevokedText, Toast.LENGTH_SHORT).show()
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            ) {
                launchCamera()
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
    ) {
        Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = "foto")
        Text("Profile photo")
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(onRegistrationSuccess = {})
}