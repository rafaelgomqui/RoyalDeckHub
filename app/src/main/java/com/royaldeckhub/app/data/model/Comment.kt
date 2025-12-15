package com.royaldeckhub.app.data.model

data class Comment(
    val id: Int,
    val elementId: Int,
    val author: String,
    val text: String,
    val timestamp: String
)
