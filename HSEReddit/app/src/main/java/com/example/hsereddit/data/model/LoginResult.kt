package com.example.hsereddit.data.model

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: AuthorizedData? = null,
    val error: Int? = null
)