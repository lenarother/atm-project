package com.example.models

data class Card(
    val id: Long, // Card number
    val pin: Int,
    val firstName: String,
    val lastName: String,
    val balance: Double,
)
