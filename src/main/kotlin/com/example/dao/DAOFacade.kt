package com.example.dao

import com.example.models.Card

interface DAOFacade {
    suspend fun allCards(): List<Card>
    suspend fun card(id: Long): Card?
    suspend fun addNewCard(pin: Int, firstName: String, lastName: String, balance: Double=0.0): Card?
    suspend fun updateCardBalance(id: Long, balance: Double): Boolean
    suspend fun deleteCard(id: Long): Boolean
}
