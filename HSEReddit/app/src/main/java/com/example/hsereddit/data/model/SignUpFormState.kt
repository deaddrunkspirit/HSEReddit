package com.example.hsereddit.data.model

data class SignUpFormState(
    val usernameError: Int? = null,
    val password1Error: Int? = null,
    val password2Error: Int? = null,
    val isDataValid: Boolean = false
)