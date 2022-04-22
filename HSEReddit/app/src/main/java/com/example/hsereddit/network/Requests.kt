package com.example.hsereddit.network

object Requests {
    class LoginRequest(
        val password: String,
        val username: String
    )

    class LogoutRequest(
        val refreshToken: String,
        val username: String
    )

    class RefreshTokenRequest(
        val refreshToken: String,
        val username: String
    )

    class SignupRequest(
        val email: String,
        val password: String
    )

    class UpdatePasswordRequest(
        val newPassword: String,
        val oldPassword: String
    )

    class CreateSectionRequest(
        val description: String,
        val id: Int,
        val name: String,
        val numberOfPosts: Int
    )

    class VoteRequest(
        val postId: Int,
        val reactionType: String,
    )

    class CreatePostRequest(
        val content: String,
        val postId: Int,
        val postTitle: String,
        val sectionId: Int
    )
}