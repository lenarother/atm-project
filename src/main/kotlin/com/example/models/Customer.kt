package com.example.models

data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String,
)

data class Card(
    val id: Int,
    val number: String,
    val customer: Customer,
    val balance: Double,
)
