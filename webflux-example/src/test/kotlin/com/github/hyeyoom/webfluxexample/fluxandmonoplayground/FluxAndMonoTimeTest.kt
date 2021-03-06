package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

@DisplayName("플럭스 모노 시간 테스트")
internal class FluxAndMonoTimeTest {

    @DisplayName("무한 수열")
    @Test
    fun testInfiniteSequence() {
        val flux = Flux.interval(Duration.ofMillis(200)).log()

        flux
                .subscribe {
                    println("Number: $it")
                }

        Thread.sleep(5000)
    }

    @DisplayName("무한 수열 테스트")
    @Test
    fun testInfiniteSequenceTest() {
        val flux = Flux.interval(Duration.ofMillis(100))
                .take(3)
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete()
    }

    @DisplayName("무한 수열 맵 테스트")
    @Test
    fun testInfiniteSequenceMapTest() {

        val flux = Flux.interval(Duration.ofMillis(100))
                .map { "$it" }
                .take(3)
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("0", "1", "2")
                .verifyComplete()
    }

    @DisplayName("무한 수열 맵 딜레이")
    @Test
    fun test() {
        val flux = Flux.interval(Duration.ofMillis(100))
                .delayElements(Duration.ofSeconds(1))
                .map { "$it" }
                .take(3)
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("0", "1", "2")
                .verifyComplete()
    }
}