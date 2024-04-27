package com.example.bankchallenge.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bankchallenge.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")


    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _emailError = mutableStateOf<Int?>(null)
    val emailError: State<Int?> = _emailError

    fun onEmailChanged(string: String) {
        _email.value = string
        validateEmail(string)
    }

    fun onPasswordChanged(string: String) {
        _password.value = string
    }

    private fun validateEmail(email: String) {
        _emailError.value = if (email.isBlank()) {
            R.string.error_email_empty
        } else if (emailPattern.matcher(email).matches()) {
            R.string.error_email_format
        } else null
    }

}