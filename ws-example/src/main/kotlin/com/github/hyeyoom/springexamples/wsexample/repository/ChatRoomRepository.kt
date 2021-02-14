package com.github.hyeyoom.springexamples.wsexample.repository

import com.github.hyeyoom.springexamples.wsexample.entity.ChatRoom
import com.github.hyeyoom.springexamples.wsexample.entity.ChatRoomManager
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import org.springframework.web.socket.WebSocketSession
import java.util.stream.Stream

private val log = KotlinLogging.logger {}

@Repository
class ChatRoomRepository(
    private val chatRoomMap: MutableMap<String, ChatRoom> = mutableMapOf(),
    private val chatRooms: MutableList<ChatRoom> = mutableListOf(),
) {
    init {
        log.info("초기화 시작=================")
        Stream.of(ChatRoomManager.create("1번방"), ChatRoomManager.create("2번방"), ChatRoomManager.create("3번방"))
            .filter { it != null }
            .forEach { chatRoomMap[it.id] = it }
        chatRooms.addAll(chatRoomMap.values)
        log.info("초기화 끝남=================")
    }

    fun getChatRoom(id: String): ChatRoom? {
        return chatRoomMap[id]
    }

    fun getChatRooms(): MutableList<ChatRoom> {
        return chatRooms
    }

    fun remove(session: WebSocketSession) {
        chatRooms.stream().forEach { it.remove(session) }
    }
}