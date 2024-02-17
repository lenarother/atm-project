package com.example.dao

import com.example.models.Cards
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseSingleton {
    fun init(dbName: String, dbUser: String, dbPass: String) {
        println("###############")
        println("dbName: $dbName")
        println("dbUser: $dbUser")
        println("dbPass: $dbPass")
        println("###############")
        val driverClassName = "org.postgresql.Driver"
        //val driverClassName = "org.h2.Driver"
        //val jdbcURL = "jdbc:h2:file:./build/db"
        val jdbcURL = "jdbc:postgresql://localhost:5432/$dbName"
        //val user = "robin"
        //val password = "12345"
        val database = Database.connect(
            jdbcURL,
            driverClassName,
            dbUser,
            dbPass
        )
        transaction(database) {
            SchemaUtils.create(Cards)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
