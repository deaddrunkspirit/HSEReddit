package com.example.hsereddit.data.model

data class Comment(
    val date: String,
    val id: Int,
    val postId: Int,
    val text: String,
    val username: User,
)
