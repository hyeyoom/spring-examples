package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration

@DisplayName("시간 테스트")
internal class VirtualTimeTest {

    @DisplayName("가상 시간 없이")
    @Test
    fun testWithoutVirtualTime() {
        val flux = Flux.interval(Duration.ofSeconds(1)).take(3).log()

        StepVerifier.create(flux)
            .expectSubscription()
            .expectNext(0, 1, 2)
            .verifyComplete()
    }

    @DisplayName("가상 시간")
    @Test
    fun testWithVirtualTime() {

        VirtualTimeScheduler.getOrSet()

        val flux = Flux.interval(Duration.ofSeconds(1)).take(3).log()

        StepVerifier.withVirtualTime { flux }
            .expectSubscription()
            .thenAwait(Duration.ofSeconds(3))
            .expectNext(0, 1, 2)
            .verifyComplete()
    }
}