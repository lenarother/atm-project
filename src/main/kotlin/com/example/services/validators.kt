package com.example.services

import com.example.models.Card

fun isCardPinValid(cardId: Long, cardPin: Int, cardStore: Map<Long, Card>): Boolean {
    val card:Card? = cardStore[cardId]
    return when {
        card == null -> false
        card.pin == cardPin -> true
        else -> false
    }
}