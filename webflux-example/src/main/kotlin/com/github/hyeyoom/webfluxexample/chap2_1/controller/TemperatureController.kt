package com.github.hyeyoom.webfluxexample.chap2_1.controller

import com.github.hyeyoom.webfluxexample.chap2_1.service.Temperature
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArraySet

private val log = KotlinLogging.logger {}

@RestController
class TemperatureController(
    private val clients: MutableSet<SseEmitter> = CopyOnWriteArraySet()
) {

    @GetMapping("/temperature-stream")
    fun events(): SseEmitter {
        val sseEmitter = SseEmitter()
        clients.add(sseEmitter)
        sseEmitter.onTimeout { clients.remove(sseEmitter) }
        sseEmitter.onCompletion { clients.remove(sseEmitter) }
        return sseEmitter
    }

    @Async
    @EventListener
    fun handleMessage(temperature: Temperature) {
        val deadEmitters = mutableListOf<SseEmitter>()
        clients.forEach {
            try {
                it.send(temperature, MediaType.APPLICATION_JSON)
            } catch (e: Exception) {
                deadEmitters.add(it)
            }
        }
        clients.removeAll(deadEmitters)
    }
}