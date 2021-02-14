package com.github.hyeyoom.springexamples.wsexample.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hyeyoom.springexamples.wsexample.entity.ChatMessage
import com.github.hyeyoom.springexamples.wsexample.repository.ChatRoomRepository
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.lang.RuntimeException

private val log = KotlinLogging.logger {}

@Profile("!stomp")
@Component
class ChatHandler(
    private val repository: ChatRoomRepository,
    private val objectMapper: ObjectMapper
) : TextWebSocketHandler() {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        log.info("payload: ${message.payload}")

        val chatMessage: ChatMessage = objectMapper.readValue(message.payload, ChatMessage::class.java)
        val chatRoom = repository.getChatRoom(chatMessage.chatRoomId) ?: throw RuntimeException("wtf?")
        chatRoom.handleMessage(session, chatMessage, objectMapper)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        repository.remove(session)
    }
}