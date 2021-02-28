package com.github.hyeyoom.slick.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class MessageProducer(
    private val template: KafkaTemplate<String, String>
) {

    fun sendMessage(message: String) {
        val sendFuture = template.send("chat-message", message)
        sendFuture.addCallback(
            {
                println("Send message=[$message] with offset=[${it?.recordMetadata?.offset()}]")
            },
            {
                println("Unable to send message=[$message] due to ${it.message}")
            }
        )

    }
}