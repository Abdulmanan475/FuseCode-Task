package com.fuse.code.order.processing.database.tables

import org.jetbrains.exposed.sql.Table

object OrdersTable : Table("orders") {
    val id = varchar("id", 36)
    val customerId = varchar("customer_id", 100)

    override val primaryKey = PrimaryKey(id)
}