package com.example

import com.example.dao.DatabaseSingleton
import com.example.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>) {
    // val customerStorage = mutableListOf<Customer>()
    io.ktor.server.netty.EngineMain.main(args)
}
// hikari

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    // initialize db connection
    val dbName = environment.config.propertyOrNull("db.dbName")?.getString() ?: ""
    val dbUser = environment.config.propertyOrNull("db.dbUser")?.getString() ?: ""
    val dbPass = environment.config.propertyOrNull("db.dbPass")?.getString() ?: ""

    println("###############")
    println("dbName: $dbName")
    println("dbUser: $dbUser")
    println("dbPass: $dbPass")
    println("###############")
    DatabaseSingleton.init(dbName, dbUser, dbPass)

    configureRouting()
}
