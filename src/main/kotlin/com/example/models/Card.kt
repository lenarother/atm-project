package com.example.models
import org.jetbrains.exposed.sql.Table

data class Card(
    val id: Long, // Card number
    val pin: Int,
    val firstName: String,
    val lastName: String,
    var balance: Double,
)

object Cards : Table() {
    val id = long("id").autoIncrement()
    val pin = integer("pin")
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val balance = double("balance")

    override val primaryKey = PrimaryKey(id)
}