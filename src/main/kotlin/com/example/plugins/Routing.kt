package com.example.plugins

import com.example.dto.*
import com.example.models.Card
import com.example.services.isCardPinValid
import com.example.services.isWithdrawCashPossible
import com.example.services.withdrawCash
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val cardStore = mapOf<Long, Card>(
    1111_1111_1111_1111 to Card(id = 1111_1111_1111_1111, pin = 1234, firstName = "Jane", lastName = "Doe", balance = 300.0),
    2222_2222_2222_2222 to Card(id = 2222_2222_2222_2222, pin = 1234, firstName = "John", lastName = "Doe", balance = 200.0),
)

fun Application.configureRouting() {
    routing {
        /**
         * Root: welcome message.
         * Required data: id, pin.
         *
         * Test:
         * Open project in browser.
         * curl http://0.0.0.0:8080/ -v
         */
        get("/") {
            call.respondText("Welcome to ATM project!")
        }
        /**
         * Card details : id (card number), first_name, last_name.
         * Required data: id, pin.
         *
         * Test:
         * curl
         *     -d '{"id":"1111111111111111", "pin":"1234"}'
         *     -H "Content-Type: application/json"
         *     -X POST http://0.0.0.0:8080/card-detail -v
         */
        post("/card-detail") {
            val cardRequest = call.receive<CardRequestDTO>()
            val isValid = isCardPinValid(cardRequest.id, cardRequest.pin, cardStore)

            if (!isValid) {
                call.respond(HttpStatusCode.Unauthorized, ErrorCardResponseDTO("Invalid PIN or CardID."))
            }
            val card: Card = cardStore[cardRequest.id]!!
            if (isValid) {
                val cardDetailResponse = CardDetailResponseDTO(card.id, card.firstName, card.lastName)
                call.respond(HttpStatusCode.OK, cardDetailResponse)
            }
        }

        /**
         * Card balance : id (card number), balance (available cash).
         * Required data: id, pin.
         *
         * Test:
         * curl
         *     -d '{"id":"1111111111111111", "pin":"1234"}'
         *     -H "Content-Type: application/json"
         *     -X POST http://0.0.0.0:8080/card-balance -v
         */
        post("/card-balance") {
            val cardRequest = call.receive<CardRequestDTO>()
            val isValid = isCardPinValid(cardRequest.id, cardRequest.pin, cardStore)

            if (!isValid) {
                call.respond(HttpStatusCode.Unauthorized, ErrorCardResponseDTO("Invalid PIN or CardID."))
            }
            val card: Card = cardStore[cardRequest.id]!!
            if (isValid) {
                val cardBalanceResponse = CardBalanceResponseDTO(card.id, card.balance)
                call.respond(HttpStatusCode.OK, cardBalanceResponse)
            }
        }

        /**
         * Withdraw cash : id (card number), balance (available cash), cash (withdrawn cash).
         * Required data: id, pin, cash.
         *
         * Test:
         * curl
         *     -d '{"id":"1111111111111111", "pin":"1234", "cash":"100.0"}'
         *     -H "Content-Type: application/json"
         *     -X PATCH http://0.0.0.0:8080/card-withdraw-cash -v
         */
        patch("/card-withdraw-cash") {
            val cardWithdrawCashRequest = call.receive<CardWithdrawCashRequestDTO>()
            val isValid = isCardPinValid(cardWithdrawCashRequest.id, cardWithdrawCashRequest.pin, cardStore)

            if (!isValid) {
                call.respond(HttpStatusCode.Unauthorized, ErrorCardResponseDTO("Invalid PIN or CardID."))
            }

            val card: Card = cardStore[cardWithdrawCashRequest.id]!!
            val canWithdraw = isWithdrawCashPossible(card, cardWithdrawCashRequest.cash)

            if (!canWithdraw) {
                call.respond(HttpStatusCode.BadRequest, ErrorCardResponseDTO("Could not withdraw requested amount. Balance too low."))
            }

            if (isValid and canWithdraw) {
                val cash = withdrawCash(card, cardWithdrawCashRequest.cash)
                val cardWithdrawCashResponse = CardWithdrawCashResponseDTO(card.id, card.balance, cash)
                call.respond(HttpStatusCode.NoContent, cardWithdrawCashResponse)
            }
        }
    }
}
