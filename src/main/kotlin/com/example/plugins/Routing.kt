package com.example.plugins

import com.example.models.Card
import com.example.models.Customer
import com.example.dto.CustomerBalance
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val customerStore = mapOf(
    1 to Customer(id = 1, firstName = "Magda", lastName = "Rother"),
    2 to Customer(id = 2, firstName = "Uche", lastName = "Powers"),
)
val cardStore = mapOf<Int, Card>(
    1 to Card(id = 1, number = "DE10101010101", customer = customerStore.get(1)!!, balance = 300.0),
    2 to Card(id = 2, number = "DE20202020202", customer = customerStore.get(2)!!, balance = 200.0),
)

fun getCustomerCard(customer: Customer, cardStore: Map<Int, Card>): Card?? {
    for (card in cardStore.values) {
        if (card.customer == customer) {
            return card
        }
    }
    return null
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World! ${customerStore.get(1)}, ${cardStore.get(2)}")
        }
        get("/balance/{id}") {
            val customerId = call.parameters["id"]?.toInt()
            // if no id rerun empty
            val customer = customerStore.get(customerId)
            val card = getCustomerCard(customer!!, cardStore)
            val balance = CustomerBalance(
                firstName = customer.firstName,
                lastName = customer.lastName,
                cardNumber = card!!.number,
                balance = card!!.balance,
            )
            println(balance)
            call.respond(balance)
        }
    }
}
