package com.example.bankchallenge.ui.screens.registration

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankchallenge.R
import com.example.bankchallenge.domain.common.Result
import com.example.bankchallenge.domain.common.States
import com.example.bankchallenge.domain.common.Validators
import com.example.bankchallenge.domain.usescases.RegisterUserUseCase
import com.example.bankchallenge.utils.UriProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    uriProvider: UriProvider
) : ViewModel() {

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

    private val _failureRegister = mutableStateOf<Int?>(null)
    val failureRegister: State<Int?> = _failureRegister

    private val _registerUiStates = MutableStateFlow<States>(States.Idle)
    val registerUiStates: StateFlow<States> = _registerUiStates.asStateFlow()

    private val validators = Validators()

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
        _emailError.value = validators.validateEmail(email)
    }

    fun onPasswordChanged(pass: String) {
        _password.value = pass
        _passwordError.value = validators.validatePassword(pass)
    }

    fun onRegisterClick(onSuccessfulLogin: () -> Unit) {
        _registerUiStates.value = States.Loading
        viewModelScope.launch {
            when (registerUser.register(
                _email.value,
                _password.value,
                _name.value,
                _surname.value
            )) {
                is Result.Error -> {
                    _registerUiStates.value = States.Idle
                    onFailureRegister()
                }

                is Result.Success -> onSuccessfulLogin()
            }
        }
    }


    private fun onFailureRegister() {
        _failureRegister.value = R.string.register_error
    }

}