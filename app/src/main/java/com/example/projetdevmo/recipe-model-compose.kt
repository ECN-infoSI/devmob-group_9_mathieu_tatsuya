package com.example.yourapp.models

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val level: String,
    val time: String
)
