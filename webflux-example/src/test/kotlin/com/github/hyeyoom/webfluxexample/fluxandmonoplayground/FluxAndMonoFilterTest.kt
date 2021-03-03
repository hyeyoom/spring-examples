package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@DisplayName("필터를 돌려본다.")
internal class FluxAndMonoFilterTest {

    private val names = mutableListOf("adam", "anna", "jack", "jenny")

    @DisplayName("필터 테스트1")
    @Test
    fun testFilter1() {

        val flux = Flux.fromIterable(names)
            .filter { it.startsWith("a") }
            .log()

        StepVerifier.create(flux)
            .expectNext("adam", "anna")
            .verifyComplete()
    }

    @DisplayName("필터 테스트2")
    @Test
    fun testFilter2() {
        val flux = Flux.fromIterable(names)
            .filter { it.length > 4 }
            .log()

        StepVerifier.create(flux)
            .expectNext("jenny")
            .verifyComplete()
    }
    

}