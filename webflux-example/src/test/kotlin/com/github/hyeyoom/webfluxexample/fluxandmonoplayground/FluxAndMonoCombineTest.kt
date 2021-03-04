package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

@DisplayName("플럭스 모노 결합")
internal class FluxAndMonoCombineTest {

    @DisplayName("merge 테스트")
    @Test
    fun testUsingMerge() {

        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("D", "E", "F")

        val merged = Flux.merge(flux1, flux2)

        StepVerifier.create(merged.log())
            .expectSubscription()
            .expectNext("A", "B", "C", "D", "E", "F")
            .verifyComplete()
    }

    @DisplayName("delay")
    @Test
    fun testDelay() {

        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1))

        val merged = Flux.merge(flux1, flux2)

        StepVerifier.create(merged.log())
            .expectSubscription()
            .expectNextCount(6)
            //.expectNext("A", "B", "C", "D", "E", "F")
            .verifyComplete()
    }

    @DisplayName("concat")
    @Test
    fun testConcat() {

        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("D", "E", "F")

        val concat = Flux.concat(flux1, flux2)

        StepVerifier.create(concat.log())
            .expectSubscription()
            .expectNext("A", "B", "C", "D", "E", "F")
            .verifyComplete()
    }

    @DisplayName("concat with delay")
    @Test
    fun testConcatWithDelay() {

        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1))

        val concat = Flux.concat(flux1, flux2)

        StepVerifier.create(concat.log())
            .expectSubscription()
            .expectNext("A", "B", "C", "D", "E", "F")
            .verifyComplete()
    }

    @DisplayName("zip")
    @Test
    fun testUsingZip() {

        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("D", "E", "F")

        val zipped = Flux.zip(flux1, flux2, { x, y -> x + y })

        StepVerifier.create(zipped.log())
            .expectSubscription()
            .expectNext("AD", "BE", "CF")
            .verifyComplete()
    }
}