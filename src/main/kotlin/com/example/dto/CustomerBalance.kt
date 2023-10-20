package com.example.dto
import kotlinx.serialization.*

@Serializable
data class CustomerBalance(
    val firstName: String,
    val lastName: String,
    val cardNumber: String,
    val balance: Double,
)