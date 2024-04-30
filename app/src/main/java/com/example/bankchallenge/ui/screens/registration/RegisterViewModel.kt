package com.example.bankchallenge.ui.screens.registration

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankchallenge.R
import com.example.bankchallenge.domain.usescases.RegisterUserUseCase
import com.example.bankchallenge.utils.UriProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    uriProvider: UriProvider
) : ViewModel() {

    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private val passwordPattern =
        Pattern.compile("^(?=.*[A-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=\\S+\$).{6,}\$")

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _surname = mutableStateOf("")
    val surname: State<String> = _surname

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _emailError = mutableStateOf<Int?>(null)
    val emailError: State<Int?> = _emailError

    private val _passwordError = mutableStateOf<Int?>(null)
    val passwordError: State<Int?> = _passwordError

    private val _availableUri = mutableStateOf(Uri.EMPTY)
    val availableUri: State<Uri> = _availableUri

    private val _uriError = mutableStateOf<Int?>(null)
    val uriError: State<Int?> = _uriError

    private val _uri = mutableStateOf<Uri?>(null)
    val uri: State<Uri?> = _uri

    init {
        _availableUri.value = uriProvider.newUri()
    }

    fun onUriLoaded(uri: Uri?) {
        if (uri != null) {
            _uriError.value = null
            _uri.value = uri
        } else {
            _uriError.value = R.string.register_error_photo
        }
    }

    fun onNameChanged(name: String) {
        _name.value = name
    }

    fun onSurnameChanged(surname: String) {
        _surname.value = surname
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        validateEmail(email)
    }

    fun onPasswordChanged(pass: String) {
        _password.value = pass
        validatePassword(pass)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            registerUser.register(_email.value, _password.value, _name.value, _surname.value)
        }
    }


    private fun validateEmail(email: String) {
        _emailError.value = if (email.isBlank()) {
            R.string.error_email_empty
        } else if (!emailPattern.matcher(email).matches()) {
            R.string.error_email_format
        } else null
    }

    private fun validatePassword(password: String) {
        _passwordError.value = if (password.isBlank()) {
            R.string.error_password_empty
        } else if (!passwordPattern.matcher(password).matches()) {
            R.string.error_password_format
        } else null
    }
}