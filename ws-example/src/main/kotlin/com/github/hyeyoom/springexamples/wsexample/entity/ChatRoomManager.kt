package com.github.hyeyoom.springexamples.wsexample.entity

import java.util.*

class ChatRoomManager {
    companion object {
        fun create(name: String): ChatRoom {
            return ChatRoom(id = UUID.randomUUID().toString(), name)
        }
    }
}