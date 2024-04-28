package com.example.bankchallenge.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankchallenge.R
import com.example.bankchallenge.domain.usescases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private val passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=\\S+\$).{6,}\$")



    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _emailError = mutableStateOf<Int?>(null)
    val emailError: State<Int?> = _emailError

    private val _passwordError = mutableStateOf<Int?>(null)
    val passwordError: State<Int?> = _passwordError

    fun onEmailChanged(email: String) {
        _email.value = email
        validateEmail(email)
    }

    fun onPasswordChanged(pass: String) {
        _password.value = pass
        validatePassword(pass)
    }

    fun loginClick(){
        viewModelScope.launch{
            loginUseCase.login(_email.value,_password.value)
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