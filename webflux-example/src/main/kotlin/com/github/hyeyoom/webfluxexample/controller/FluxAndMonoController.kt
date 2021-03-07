package com.github.hyeyoom.webfluxexample.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
class FluxAndMonoController {

    @GetMapping("/flux", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getFlux(): Flux<Int> {
        return Flux.just(1, 2, 3, 4)
            .delayElements(Duration.ofSeconds(1))
            .log()
    }

    @GetMapping("/flux-stream", produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getFluxStream(): Flux<Long> {
        return Flux.interval(Duration.ofSeconds(1))
            .log()
    }

    @GetMapping("/mono")
    fun getMono(): Mono<Int> {
        return Mono.just(1).log()
    }
}