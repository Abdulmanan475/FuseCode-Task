package com.fuse.code.order.processing

import com.fuse.code.order.processing.routes.authRoutes
import com.fuse.code.order.processing.routes.orderRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRoutes()
        orderRoutes()
    }
}
