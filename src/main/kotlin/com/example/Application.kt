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

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    DatabaseSingleton.init()
    configureRouting()
}
