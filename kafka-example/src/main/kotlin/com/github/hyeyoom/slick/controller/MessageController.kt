package com.github.hyeyoom.slick.controller

import com.github.hyeyoom.slick.service.MessageProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/message")
class MessageController(
    private val producer: MessageProducer
) {

    @PostMapping
    fun message(@RequestBody message: Message) {
        producer.sendMessage(message.message)
    }
}

data class Message(
    val message: String
)