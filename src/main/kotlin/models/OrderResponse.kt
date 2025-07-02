package com.fuse.code.order.processing.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val orderId: String,
    val message: String
)
