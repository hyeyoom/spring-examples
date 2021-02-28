package com.github.hyeyoom.webfluxexample.chap2_1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class WebfluxExampleChap2Application

fun main(args: Array<String>) {
    runApplication<WebfluxExampleChap2Application>(*args)
}
