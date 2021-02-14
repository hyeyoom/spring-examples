package com.github.hyeyoom.springexamples.wsexample.utils

import mu.KotlinLogging
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException

private val log = KotlinLogging.logger {}

class MessageSendUtils {
    companion object {
        fun sendMessage(session: WebSocketSession, message: TextMessage?) {
            try {
                session.sendMessage(message!!)
            } catch (e: IOException) {
                log.error(e.message, e)
            }
        }
    }
}