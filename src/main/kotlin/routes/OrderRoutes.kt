package com.fuse.code.order.processing.routes

import com.fuse.code.order.processing.models.OrderRequest
import com.fuse.code.order.processing.models.OrderResponse
import com.fuse.code.order.processing.services.OrderService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRoutes(){
    authenticate("auth-jwt") {
        post("/order") {
            val orderRequest = call.receive<OrderRequest>()

            if (orderRequest.customerId.isBlank() || orderRequest.items.isEmpty()
                || orderRequest.items.any { it.sku.isBlank() || it.quantity <= 0 }
            ) {
                call.respondText(
                    "Invalid order input",
                    ContentType.Text.Plain,
                    HttpStatusCode.BadRequest
                )

                return@post
            }

            val orderId = OrderService.createOrder(orderRequest)

            call.respond(
                HttpStatusCode.OK,
                OrderResponse(orderId, "Order received successfully.")
            )
        }
    }
}