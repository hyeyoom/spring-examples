package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.random.Random

@DisplayName("플럭스&모노 팩토리")
internal class FluxAndMonoFactoryTest {

    private val names = mutableListOf("adam", "anna", "jack", "jenny")

    @DisplayName("이터러블 플럭스")
    @Test
    fun fluxUsingIterable() {
        val flux = Flux.fromIterable(names).log()

        StepVerifier.create(flux)
                .expectNext("adam", "anna", "jack", "jenny")
                .verifyComplete()
    }

    @DisplayName("어레이 플럭스")
    @Test
    fun fluxUsingArray() {

        val names = arrayOf("adam", "anna", "jack", "jenny")
        val flux = Flux.fromArray(names).log()

        StepVerifier.create(flux)
                .expectNext("adam", "anna", "jack", "jenny")
                .verifyComplete()
    }

    @DisplayName("플럭스트림")
    @Test
    fun fluxUsingStream() {
        val flux = Flux.fromStream(names.stream()).log()

        StepVerifier.create(flux)
                .expectNext("adam", "anna", "jack", "jenny")
                .verifyComplete()
    }

    @DisplayName("모노 널러블")
    @Test
    fun monoUsingJustOrEmpty() {
        val idk = Random.nextBoolean()
        val word = "Spring"

        val result = if (idk) word else null
        val mono = Mono.justOrEmpty(result).log()

        StepVerifier.create(mono)
                .expectNext(result)
    }

    @DisplayName("모노 서플라이어")
    @Test
    fun monoUsingSupplier() {

        val supplier = { "supplier" }

        val mono = Mono.fromSupplier(supplier).log()
        StepVerifier.create(mono)
                .expectNext("supplier")
                .verifyComplete()
    }

    @DisplayName("플럭스 레인지")
    @Test
    fun monoUsingRange() {
        val flux = Flux.range(1, 5).log()

        StepVerifier.create(flux)
                .expectNext(1,2,3,4,5)
                .verifyComplete()
    }
}