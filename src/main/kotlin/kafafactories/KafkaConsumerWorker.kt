package com.fuse.code.order.processing.kafafactories

import com.fuse.code.order.processing.models.OrderCreatedEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

object KafkaConsumerWorker {

    suspend fun startConsuming() = withContext(Dispatchers.IO) {
        val config = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_BOOTSTRAP_SERVERS") ?: "localhost:9092")
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.GROUP_ID_CONFIG, "order-consumer-group")
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        }

        val consumer = KafkaConsumer<String, String>(config)
        consumer.subscribe(listOf("order-events"))

        println("Kafka consumer started...")

        while (true) {
            val records = consumer.poll(Duration.ofMillis(100))
            for (record in records) {
                val json = record.value()
                try {
                    val event = Json.decodeFromString<OrderCreatedEvent>(json)
                    println("📥 Consumed event from Kafka: $event")
                } catch (e: Exception) {
                    println("Failed to deserialize event: $json\n${e.message}")
                }
            }
        }
    }
}