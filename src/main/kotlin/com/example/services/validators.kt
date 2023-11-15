package com.example.services

import com.example.models.Card

/**
 * Check if card with given id is available in given store and if given pin is valid.
 * Returns bool.
 */
fun isCardPinValid(cardId: Long, cardPin: Int, cardStore: Map<Long, Card>): Boolean {
    val card:Card? = cardStore[cardId]
    return when {
        card == null -> false
        card.pin == cardPin -> true
        else -> false
    }
}

/**
 * Check if withdraw operation for given card and amount of money is possible.
 * Returns bool.
 */
fun isWithdrawCashPossible(card: Card, cash: Double): Boolean {
    return card.balance >= cash
}

/**
 * Remove given amount of money from given card.
 * Returns amount of withdrawn cash.
 */
fun withdrawCash(card: Card, cash: Double): Double {
    card.balance -= cash
    return cash
}