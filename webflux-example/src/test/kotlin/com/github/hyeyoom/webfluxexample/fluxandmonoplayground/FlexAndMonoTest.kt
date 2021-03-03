package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@DisplayName("flux와 mono 테스트")
internal class FlexAndMonoTest {

    @DisplayName("flux test")
    @Test
    fun testFlux() {
        val flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .map { "$it Flux" }
            /*.concatWith(Flux.just("How", "Cool", "Is", "that?"))*/
            /*.mergeWith(Flux.just("New Data", "How", "Cool", "Is", "That"))*/
            /*.concatWith(Flux.error(RuntimeException("Exception Occurred?")))
            .concatWith(Flux.just("After Error"))*/
            .log()

        flux
            .subscribe(
                { msg -> println(msg) },
                { e -> println(e) },
                { println("Completed!") }
            )
    }

    @DisplayName("에러 없이 flux 사용하기")
    @Test
    fun testFluxWithoutError() {

        val flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .log()

        StepVerifier.create(flux)
            .expectNext("Spring")
            .expectNext("Spring Boot")
            .expectNext("Reactive Spring")
            .verifyComplete()
    }

    @DisplayName("에러와 함께하는 flux 사용하기1")
    @Test
    fun testFluxWithError1() {

        val flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .concatWith(Flux.error(RuntimeException("Exception Occurred?")))
            .log()

        StepVerifier.create(flux)
            .expectNextCount(3)
            /*.expectNext("Spring")
            .expectNext("Spring Boot")
            .expectNext("Reactive Spring")*/
            /*.expectError(RuntimeException::class.java)*/
            .expectErrorMessage("Exception Occurred?")
            .verify()
    }

    @DisplayName("에러와 함께하는 flux 사용하기2")
    @Test
    fun testFluxWithError2() {

        val flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
            .concatWith(Flux.error(RuntimeException("Exception Occurred?")))
            .log()

        StepVerifier.create(flux)
            .expectNextCount(3)
            /*.expectNext("Spring")
            .expectNext("Spring Boot")
            .expectNext("Reactive Spring")*/
            /*.expectError(RuntimeException::class.java)*/
            .expectErrorMessage("Exception Occurred?")
            .verify()
    }

    @DisplayName("모노 테스트")
    @Test
    fun testMono() {
        val mono = Mono.just("Spring").log()

        StepVerifier.create(mono)
                .expectNext("Spring")
                .verifyComplete()
    }

    @DisplayName("모노 테스트 에러 발생")
    @Test
    fun testMonoWithError() {
        val mono = Mono.just("Spring")
                .concatWith(Mono.error(RuntimeException()))
                .log()

        StepVerifier.create(mono)
                .expectError(RuntimeException::class.java)
    }
}