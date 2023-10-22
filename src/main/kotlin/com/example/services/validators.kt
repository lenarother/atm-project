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

fun isWithdrawCashPossible(card: Card, cash: Double): Boolean {
    return card.balance >= cash
}

fun withdrawCash(card: Card, cash: Double): Double {
    card.balance -= cash
    return cash
}