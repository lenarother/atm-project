package com.example.dto
import kotlinx.serialization.Serializable

@Serializable
data class CardResponseDTO<T>(
    val message: String,
    val body: T,
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

@Serializable
data class CardBalanceResponseDTO(
    val cardNumber: Long,
    val balance: Double,
)

@Serializable
data class CardWithdrawCashResponseDTO(
    val cardNumber: Long,
    val balance: Double,
    val cash: Double,
)