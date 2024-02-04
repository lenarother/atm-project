package com.example.dao

import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.Card
import com.example.models.Cards
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class DAOFacadeImpl : DAOFacade {
    private fun resultRowToCard(row: ResultRow) = Card(
        id = row[Cards.id],
        pin = row[Cards.pin],
        firstName = row[Cards.firstName],
        lastName = row[Cards.lastName],
        balance = row[Cards.balance],
    )

    override suspend fun allCards(): List<Card> = dbQuery {
        Cards.selectAll().map(::resultRowToCard)
    }

    override suspend fun card(id: Long): Card? = dbQuery {
        Cards
            .select { Cards.id eq id }
            .map(::resultRowToCard)
            .singleOrNull()
    }

    override suspend fun addNewCard(pin: Int, firstName: String, lastName: String, balance: Double): Card? = dbQuery {
        val insertStatement = Cards.insert {
            it[Cards.pin] = pin
            it[Cards.firstName] = firstName
            it[Cards.lastName] = lastName
            it[Cards.balance] = balance
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCard)
    }

    override suspend fun updateCardBalance(id: Long, balance: Double): Boolean = dbQuery {
        Cards.update({ Cards.id eq id }) {
            it[Cards.balance] = balance
        } > 0
    }

    override suspend fun deleteCard(id: Long): Boolean = dbQuery {
        Cards.deleteWhere { Cards.id eq id } > 1
    }

}