package com.github.hyeyoom.springexamples.wsexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WsExampleApplication

fun main(args: Array<String>) {
    runApplication<WsExampleApplication>(*args)
}
