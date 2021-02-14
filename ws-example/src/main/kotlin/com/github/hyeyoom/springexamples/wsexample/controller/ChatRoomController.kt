package com.github.hyeyoom.springexamples.wsexample.controller

import com.github.hyeyoom.springexamples.wsexample.repository.ChatRoomRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.concurrent.atomic.AtomicInteger

private val log = KotlinLogging.logger {}

@Controller
@RequestMapping("/chat")
class ChatRoomController(
    val repository: ChatRoomRepository,
    @Value("\${viewname.chatroom.list}") val listViewName: String,
    @Value("\${viewname.chatroom.detail}") val detailViewName: String,
    val seq: AtomicInteger = AtomicInteger(0)
) {
    @GetMapping("/rooms")
    fun rooms(model: Model): String? {
        model.addAttribute("rooms", repository.getChatRooms())
        return listViewName
    }

    @GetMapping("/rooms/{id}")
    fun room(@PathVariable id: String, model: Model): String? {
        val room = repository.getChatRoom(id)
        model.addAttribute("room", room)
        model.addAttribute("member", "member" + seq.incrementAndGet())
        return detailViewName
    }
}