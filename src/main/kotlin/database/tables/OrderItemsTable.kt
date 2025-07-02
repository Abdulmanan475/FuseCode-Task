package com.fuse.code.order.processing.database.tables

import org.jetbrains.exposed.sql.Table

object OrderItemsTable : Table("order_items") {
    val orderId = varchar("order_id", 36).references(OrdersTable.id)
    val sku = varchar("sku", 100)
    val quantity = integer("quantity")
}