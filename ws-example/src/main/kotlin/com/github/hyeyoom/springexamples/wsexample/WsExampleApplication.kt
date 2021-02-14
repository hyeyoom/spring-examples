package com.github.hyeyoom.springexamples.wsexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
@SpringBootApplication
class WsExampleApplication {
    @GetMapping("/")
    fun index(): String {
        return "redirect:/chat/rooms"
    }
}

fun main(args: Array<String>) {
    runApplication<WsExampleApplication>(*args)
}
