package com.fuse.code.order.processing.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fuse.code.order.processing.models.LoginRequest
import com.fuse.code.order.processing.models.TokenResponse
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.authRoutes(){
    post("/login") {
        val request = call.receive<LoginRequest>()

        if (request.username == "admin" && request.password == "password") {
            val config = call.application.environment.config
            val jwtAudience = config.property("jwt.audience").getString()
            val jwtIssuer = config.property("jwt.issuer").getString()
            val jwtSecret = config.property("jwt.secret").getString()

            val token = JWT.create()
                .withAudience(jwtAudience)
                .withIssuer(jwtIssuer)
                .withClaim("username", request.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000 * 60)) // 1 hour
                .sign(Algorithm.HMAC256(jwtSecret))

            call.respond(TokenResponse(token))
        } else {
            call.respondText("Invalid credentials", status = io.ktor.http.HttpStatusCode.Unauthorized)
        }
    }
}