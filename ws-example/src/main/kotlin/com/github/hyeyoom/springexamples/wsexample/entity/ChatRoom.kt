package com.github.hyeyoom.springexamples.wsexample.entity

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hyeyoom.springexamples.wsexample.utils.MessageSendUtils
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession


class ChatRoom(
    val id: String,
    val name: String,
    private val sessions: MutableSet<WebSocketSession> = mutableSetOf()
) {
    fun handleMessage(session: WebSocketSession, chatMessage: ChatMessage, mapper: ObjectMapper) {
        if (chatMessage.type == MessageType.JOIN) {
            join(session)
            chatMessage.message = "${chatMessage.writer}님이 입장했습니다."
        }
        send(chatMessage, mapper)
    }

    private fun join(session: WebSocketSession) {
        sessions.add(session)
    }

    private fun <T> send(messageObject: T, objectMapper: ObjectMapper) {
        try {
            val message = TextMessage(objectMapper.writeValueAsString(messageObject))
            sessions.parallelStream().filter {
                it != null
            }.forEach { session: WebSocketSession ->
                MessageSendUtils.sendMessage(session, message)
            }
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e.message, e)
        }
    }

    fun remove(target: WebSocketSession) {
        val targetId = target.id
        sessions.removeIf { session: WebSocketSession -> session.id == targetId }
    }
}