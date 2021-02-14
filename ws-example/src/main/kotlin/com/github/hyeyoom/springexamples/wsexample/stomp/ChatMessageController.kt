package com.github.hyeyoom.springexamples.wsexample.stomp

import com.github.hyeyoom.springexamples.wsexample.entity.ChatMessage
import org.springframework.context.annotation.Profile
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@Profile("stomp")
@RestController
class ChatMessageController(
    val template: SimpMessagingTemplate
) {

    @MessageMapping("/chat/join")
    fun join(message: ChatMessage) {
        message.message = "${message.writer}님이 입장하셨습니다."
        template.convertAndSend("/subscribe/chat/room/${message.chatRoomId}", message)
    }

    @MessageMapping("/chat/message")
    fun message(message: ChatMessage) {
        template.convertAndSend("/subscribe/chat/room/${message.chatRoomId}", message)
    }
}