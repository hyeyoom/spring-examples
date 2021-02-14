package com.github.hyeyoom.springexamples.wsexample.entity

data class ChatMessage(
    var chatRoomId: String = "",
    var writer: String = "",
    var message: String = "",
    var type: MessageType = MessageType.JOIN
)