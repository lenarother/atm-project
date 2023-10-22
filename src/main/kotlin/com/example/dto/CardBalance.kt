package com.example.dto
import kotlinx.serialization.Serializable

@Serializable
data class CardBalance(
    val firstName: String,
    val lastName: String,
    val cardNumber: Long,
    val balance: Double,
)

@Serializable
data class CardDetailRequestDTO(
    val id: Long,
    val pin: Int,
)
@Serializable
data class ErrorCardResponseDTO(
    val message: String,
)

@Serializable
data class CardDetailResponseDTO(
    val cardNumber: Long,
    val firstName: String,
    val lastName: String,
)

