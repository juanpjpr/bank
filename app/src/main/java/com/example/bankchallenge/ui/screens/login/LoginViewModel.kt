package com.example.bankchallenge.ui.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankchallenge.R
import com.example.bankchallenge.domain.common.ProcessState
import com.example.bankchallenge.domain.common.Result
import com.example.bankchallenge.domain.common.Validator
import com.example.bankchallenge.domain.usescases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _emailError = mutableStateOf<Int?>(null)
    val emailError: State<Int?> = _emailError

    private val _passwordError = mutableStateOf<Int?>(null)
    val passwordError: State<Int?> = _passwordError

    private val _loginError = mutableStateOf<Int?>(null)
    val loginError: State<Int?> = _loginError

    private val _loginUiProcessState = mutableStateOf<ProcessState>(ProcessState.Idle)
    val loginUiProcessState: State<ProcessState> = _loginUiProcessState

    private val validator = Validator()
    fun onEmailChanged(email: String) {
        _email.value = email
        _emailError.value = validator.validateEmail(email)
    }

    fun onPasswordChanged(pass: String) {
        _password.value = pass
        _passwordError.value = validator.validatePassword(pass)
    }

    fun loginClick(onSuccessfulLogin: () -> Unit) {
        _loginUiProcessState.value = ProcessState.Loading
        viewModelScope.launch {
            when (val res = loginUseCase.login(_email.value, _password.value)) {
                is Result.Error -> {
                    _loginUiProcessState.value = ProcessState.Error(res.errorMessage!!)
                    onFailureLogin()
                }

                is Result.Success -> onSuccessfulLogin()
            }
        }
    }

    private fun onFailureLogin() {
        _loginError.value = R.string.error_login
    }


}