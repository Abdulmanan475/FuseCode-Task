package com.fuse.code.order.processing.database

import com.fuse.code.order.processing.database.tables.OrderItemsTable
import com.fuse.code.order.processing.database.tables.OrdersTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;"
            driverClassName = "org.h2.Driver"
            maximumPoolSize = 5
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(OrdersTable, OrderItemsTable)
        }
    }
}