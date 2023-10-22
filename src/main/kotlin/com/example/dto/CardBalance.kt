package com.example.dto
import kotlinx.serialization.Serializable

@Serializable
data class CardBalance(
    val firstName: String,
    val lastName: String,
    val cardNumber: Long,
    val balance: Double,
)
