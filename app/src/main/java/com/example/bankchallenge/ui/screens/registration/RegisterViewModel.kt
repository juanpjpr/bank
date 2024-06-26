package com.example.bankchallenge.ui.screens.registration

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankchallenge.R
import com.example.bankchallenge.domain.common.ProcessState
import com.example.bankchallenge.domain.common.Result
import com.example.bankchallenge.domain.common.Validator
import com.example.bankchallenge.domain.usescases.RegisterUserUseCase
import com.example.bankchallenge.utils.UriProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase, uriProvider: UriProvider
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _surname = mutableStateOf("")
    val surname: State<String> = _surname

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _nameError = mutableStateOf<Int?>(null)
    val nameError: State<Int?> = _nameError

    private val _surnameError = mutableStateOf<Int?>(null)
    val surnameError: State<Int?> = _surnameError

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

    private val _registerUiProcessState = mutableStateOf<ProcessState>(ProcessState.Idle)
    val registerUiProcessState: State<ProcessState> = _registerUiProcessState

    private val validator = Validator()

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

    fun onErrorDismiss() {
        _registerUiProcessState.value = ProcessState.Idle
    }

    fun onNameChanged(name: String) {
        _name.value = name
        _nameError.value = validator.validateName(name)
    }

    fun onSurnameChanged(surname: String) {
        _surname.value = surname
        _surnameError.value = validator.validateSurname(surname)
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        _emailError.value = validator.validateEmail(email)
    }

    fun onPasswordChanged(pass: String) {
        _password.value = pass
        _passwordError.value = validator.validatePassword(pass)
    }

    fun onRegisterClick(onSuccessfulLogin: () -> Unit) {
        _registerUiProcessState.value = ProcessState.Loading

        executeMandatoryValidations()
        if (fieldsAreInvalid()) {
            _registerUiProcessState.value = ProcessState.Idle
            return
        }
        viewModelScope.launch {
            when (registerUser.register(
                _email.value, _password.value, _name.value, _surname.value
            )) {
                is Result.Error -> {
                    _registerUiProcessState.value = ProcessState.Idle
                    onFailureRegister()
                }

                is Result.Success -> {
                    _registerUiProcessState.value = ProcessState.Idle
                    onSuccessfulLogin()
                }
            }
        }
    }

    private fun executeMandatoryValidations() {
        _nameError.value = validator.validateName(_name.value)
        _surnameError.value = validator.validateSurname(_surname.value)
        _emailError.value = validator.validateEmail(_email.value)
        _passwordError.value = validator.validatePassword(_password.value)
        _uriError.value = if (_uri.value == null) {
            R.string.error_uri
        } else null

    }

    private fun fieldsAreInvalid() =
        _nameError.value != null || _surnameError.value != null || _emailError.value != null || _passwordError.value != null || _uriError.value != null

    private fun onFailureRegister() {
        _failureRegister.value = R.string.register_error
    }

}