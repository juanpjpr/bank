package com.example.bankchallenge.domain.common

import com.example.bankchallenge.R
import java.util.regex.Pattern

class Validator {
    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

    fun validateEmail(email: String) =
        if (email.isBlank()) {
            R.string.error_email_empty
        } else if (!emailPattern.matcher(email).matches()) {
            R.string.error_email_format
        } else null


    fun validatePassword(password: String) =
        if (password.isBlank()) {
            R.string.error_password_empty
        } else if (password.length < 6) {
            R.string.error_password_format
        } else null

    fun validateName(name: String) = if (name.isBlank()) {
        R.string.error_name_empty
    } else null

    fun validateSurname(name: String) = if (name.isBlank()) {
        R.string.error_surname_empty
    } else null

}