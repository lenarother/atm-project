package com.example.plugins

import com.example.models.Card
import com.example.dto.CardBalance
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


val cardStore = mapOf<Long, Card>(
    1111_1111_1111_1111 to Card(id = 1111_1111_1111_1111, pin = 1234, firstName="Jane", lastName="Doe", balance = 300.0),
    2222_2222_2222_2222 to Card(id = 2222_2222_2222_2222, pin = 1234, firstName="John", lastName="Doe", balance = 200.0),
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
    }
}
