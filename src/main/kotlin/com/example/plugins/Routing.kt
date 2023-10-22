package com.example.plugins

import com.example.dto.*
import com.example.models.Card
import com.example.services.isCardPinValid
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
        get("/") {
            call.respondText("Hello World! ${cardStore.get(1111_1111_1111_1111)}")
        }
        get("/balance/{id}") {
            val cardNumber = call.parameters["id"]?.toLong()
            // if no id rerun empty
            val card = cardStore.get(cardNumber)
            val balance = CardBalance(
                firstName = card!!.firstName,
                lastName = card!!.lastName,
                cardNumber = card!!.id,
                balance = card!!.balance,
            )
            println(balance)
            call.respond(balance)
        }
        post("/card-detail") {
            // Test with:
            // curl -d '{"id":"1111111111111111", "pin":"1234"}' -H "Content-Type: application/json" -X POST http://0.0.0.0:8080/card-detail -v

            val cardDetailRequest = call.receive<CardRequestDTO>()
            val isValid = isCardPinValid(cardDetailRequest.id, cardDetailRequest.pin, cardStore)

            if (!isValid) {
                call.respond(HttpStatusCode.Unauthorized, ErrorCardResponseDTO("Invalid PIN or CardID."))
            }
            if (isValid) {
                val card: Card? = cardStore[cardDetailRequest.id]
                val cardDetailResponse = CardDetailResponseDTO(card!!.id, card!!.firstName, card!!.lastName)
                call.respond(HttpStatusCode.OK, cardDetailResponse)
            }
        }
        post("/card-balance") {
            // Test with:
            // curl -d '{"id":"1111111111111111", "pin":"1234"}' -H "Content-Type: application/json" -X POST http://0.0.0.0:8080/card-balance -v

            val cardDetailRequest = call.receive<CardRequestDTO>()
            val isValid = isCardPinValid(cardDetailRequest.id, cardDetailRequest.pin, cardStore)

            if (!isValid) {
                call.respond(HttpStatusCode.Unauthorized, ErrorCardResponseDTO("Invalid PIN or CardID."))
            }
            if (isValid) {
                val card: Card? = cardStore[cardDetailRequest.id]
                val cardBalanceResponse = CardBalanceResponseDTO(card!!.id, card!!.balance)
                call.respond(HttpStatusCode.OK, cardBalanceResponse)
            }
        }
    }
}
