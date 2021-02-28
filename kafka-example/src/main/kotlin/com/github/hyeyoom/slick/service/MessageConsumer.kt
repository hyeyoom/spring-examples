package com.github.hyeyoom.slick.service

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class MessageConsumer {

    @KafkaListener(topics = ["chat-message"], groupId = "slick-message", containerFactory = "messageFactory")
    fun listenGroupSlickMessage(message: String) {
        log.info("Recieved message from group slick-message: $message")
    }
}