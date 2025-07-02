package com.fuse.code.order.processing.kafafactories

import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import com.fuse.code.order.processing.models.OrderCreatedEvent
import java.util.*

object KafkaProducerFactory {

    private val producer: KafkaProducer<String, String>
    private val topic: String

    init {
        val config = Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_BOOTSTRAP_SERVERS") ?: "localhost:9092")
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        }

        topic = "order-events"
        producer = KafkaProducer(config)
    }

    fun produceEvent(event: OrderCreatedEvent) {
        val json = Json.encodeToString(event)
        val record = ProducerRecord(topic, event.orderId, json)
        producer.send(record)
        println("Produced event to Kafka: $json")
    }
}