package com.example.services

import com.example.models.Card
import org.junit.Assert.assertEquals
import org.junit.Test

val testCardStore = mapOf<Long, Card>(
    1111_1111_1111_1111 to Card(id = 1111_1111_1111_1111, pin = 1234, firstName = "Jane", lastName = "Doe", balance = 300.0),
    2222_2222_2222_2222 to Card(id = 2222_2222_2222_2222, pin = 1234, firstName = "John", lastName = "Doe", balance = 200.0),
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