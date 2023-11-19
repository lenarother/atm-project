package com.example.services

import com.example.models.Card
import org.junit.Assert.assertEquals
import org.junit.Test

val testCardStore = mapOf<Long, Card>(
    81111_1111_1111_1111 to Card(id = 1111_1111_1111_1111, pin = 1234, firstName = "Jane", lastName = "Doe", balance = 300.0),
    82222_2222_2222_2222 to Card(id = 2222_2222_2222_2222, pin = 1234, firstName = "John", lastName = "Doe", balance = 200.0),
)

class ValidatorsKtTest {
    @Test
    fun testPinValid() {
        val expected = true
        val result = isCardPinValid(1111_1111_1111_1111, 1234, testCardStore)
        assertEquals(expected, result)
    }

    @Test
    fun testPinInvalid() {
        val expected = false
        val result = isCardPinValid(1111_1111_1111_1111, 5555, testCardStore)
        assertEquals(expected, result)
    }

    @Test
    fun testCardIdDoesNotExist() {
        val expected = false
        val result = isCardPinValid(9999_1111_1111_1111, 1234, testCardStore)
        assertEquals(expected, result)
    }
}

class CanWithdrawKtTest {
    @Test
    fun testCanWithdraw() {
        val expected = true
        val card: Card = testCardStore.get(1111_1111_1111_1111)!!
        val result = isWithdrawCashPossible(card, 300.0)
        assertEquals(expected, result)
    }

    @Test
    fun testCannotWithdraw() {
        val expected = false
        val card: Card = testCardStore.get(1111_1111_1111_1111)!!
        val result = isWithdrawCashPossible(card, 301.0)
        assertEquals(expected, result)
    }
}

class WithdrawKtTest {
    @Test
    fun testWithdraw() {
        val expected_cash: Double = 50.0
        val expected_balance: Double = 250.0
        val card: Card = testCardStore.get(1111_1111_1111_1111)!!
        val result = withdrawCash(card, 50.0)
        assertEquals(expected_cash, result, 0.0)
        assertEquals(expected_balance, card.balance, 0.0)
    }
}
