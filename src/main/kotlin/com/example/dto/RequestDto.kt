package com.example.dto
import kotlinx.serialization.Serializable

@Serializable
data class CardRequestDTO(
    val id: Long,
    val pin: Int,
)

@Serializable
data class CardWithdrawCashRequestDTO(
    val id: Long,
    val pin: Int,
    val cash: Double,
)
