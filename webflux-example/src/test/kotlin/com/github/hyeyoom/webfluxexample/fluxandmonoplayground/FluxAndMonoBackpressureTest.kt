package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@DisplayName("배압")
internal class FluxAndMonoBackpressureTest {

    @DisplayName("배압 코드 써보기")
    @Test
    fun backPressureTest() {

        val flux = Flux.range(1, 6).log()

        StepVerifier.create(flux)
            .expectSubscription()
            .thenRequest(1)
            .expectNext(1)
            .thenRequest(1)
            .expectNext(2)
            .thenRequest(1)
            .expectNext(3)
            .thenRequest(1)
            .expectNext(4)
            .thenRequest(2)
            .expectNext(5, 6)
            .verifyComplete()
    }

    @DisplayName("backpressure")
    @Test
    fun backpressure() {
        val flux = Flux.range(1, 10).log()

        flux.subscribe(
            { println("Element is : $it") },
            { println("Exception is : $it") },
            { println("Done") },
            { it.request(10) }
        )
    }

    @DisplayName("backpressure cancel")
    @Test
    fun backpressure_Cancel() {

        val flux = Flux.range(1, 10).log()

        flux.subscribe(
            { println("Element is : $it") },
            { println("Exception is : $it") },
            { println("Done") },
            { it.cancel() }
        )
    }

    @DisplayName("backpressure basesub")
    @Test
    fun backpressure_Basesub() {
        val flux = Flux.range(1, 10).log()

        flux.subscribe(TestSubscriber())
    }
}

class TestSubscriber : BaseSubscriber<Int>() {

    override fun hookOnSubscribe(subscription: Subscription) {
        subscription.request(1)
    }

    override fun hookOnNext(value: Int) {
        //request(1)
        println("Value: $value")
        if (value == 4) {
            cancel()
        }
    }
}