package com.fuse.code.order.processing.services

import com.fuse.code.order.processing.database.tables.OrderItemsTable
import com.fuse.code.order.processing.database.tables.OrdersTable
import com.fuse.code.order.processing.kafafactories.KafkaProducerFactory
import com.fuse.code.order.processing.models.OrderCreatedEvent
import com.fuse.code.order.processing.models.OrderRequest
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object OrderService {
    fun createOrder(request: OrderRequest): String {
        val orderId = UUID.randomUUID().toString()

        transaction {
            OrdersTable.insert {
                it[id] = orderId
                it[customerId] = request.customerId
            }

            request.items.forEach { item ->
                OrderItemsTable.insert {
                    it[OrderItemsTable.orderId] = orderId
                    it[sku] = item.sku
                    it[quantity] = item.quantity
                }
            }

        }

        KafkaProducerFactory.produceEvent(
            OrderCreatedEvent(
                orderId = orderId,
                customerId = request.customerId,
                items = request.items
            )
        )

        return orderId
    }

}