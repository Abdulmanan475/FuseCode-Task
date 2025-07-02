package com.fuse.code.order.processing.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val customerId: String,
    val items: List<OrderItem>
)
