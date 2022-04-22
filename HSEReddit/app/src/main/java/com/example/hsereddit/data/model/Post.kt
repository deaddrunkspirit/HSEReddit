package com.example.hsereddit.data.model

data class Post(
    val commentCount: Int,
    val content: String,
    val downvote: Boolean,
    val id: Int,
    val postTitle: String,
    val reactionCount: Int,
    val sectionId: Int,
    val sectionName: String,
    val upvote: Boolean,
    val username: String
)
