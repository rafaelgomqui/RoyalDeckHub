package com.royaldeckhub.app.data.model

data class Element(
    val id: Int,
    val name: String,
    val description: String,
    val rarity: String,
    val elixir: Int,
    val imageRes: Int? = null // Using null for mock, will use icons or colors
)
