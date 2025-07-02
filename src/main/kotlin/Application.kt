package com.fuse.code.order.processing

import com.fuse.code.order.processing.database.DatabaseFactory
import com.fuse.code.order.processing.kafafactories.KafkaConsumerWorker
import io.ktor.server.application.*
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureMonitoring()
    configureSecurity()
    configureSerialization()
    configureRouting()

    launch {
        KafkaConsumerWorker.startConsuming()
    }
}
