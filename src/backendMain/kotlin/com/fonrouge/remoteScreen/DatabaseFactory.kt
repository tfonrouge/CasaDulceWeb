package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.tables.CustomerOrders
import com.fonrouge.remoteScreen.tables.Customers
import com.fonrouge.remoteScreen.tables.Products
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect("jdbc:sqlite:sqlDatabase", "org.sqlite.JDBC")
        transaction(database) {
            SchemaUtils.create(CustomerOrders, Customers, Products)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
