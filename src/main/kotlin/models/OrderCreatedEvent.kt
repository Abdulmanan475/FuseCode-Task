package com.fuse.code.order.processing.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderCreatedEvent(
    val orderId: String,
    val customerId: String,
    val items: List<OrderItem>
)
