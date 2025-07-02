package com.fuse.code.order.processing.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val sku: String,
    val quantity: Int
)
