package com.example.bankchallenge.domain.common

import com.example.bankchallenge.R
import java.util.regex.Pattern

class Validator {
    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private val passwordPattern =
        Pattern.compile("^(?=.*[A-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=\\S+\$).{6,}\$")

    fun validateEmail(email: String): Int? {
        if (email.isBlank()) {
            return R.string.error_email_empty
        } else if (!emailPattern.matcher(email).matches()) {
            return R.string.error_email_format
        }
        return null
    }

    fun validatePassword(password: String): Int? {
        if (password.isBlank()) {
            return R.string.error_password_empty
        } else if (!passwordPattern.matcher(password).matches()) {
            R.string.error_password_format
        }
        return null
    }
}