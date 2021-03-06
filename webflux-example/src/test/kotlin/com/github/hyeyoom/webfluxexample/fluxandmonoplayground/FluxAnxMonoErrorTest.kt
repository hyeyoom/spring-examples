package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.kotlin.extra.retry.retryExponentialBackoff
import reactor.kotlin.extra.retry.retryRandomBackoff
import reactor.test.StepVerifier
import java.time.Duration

@DisplayName("에러 테스트")
internal class FluxAnxMonoErrorTest {

    @DisplayName("에러 핸들링")
    @Test
    fun testErrorHandling() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .concatWith(Flux.just("E"))
                .onErrorResume {
                    println("Flux.error(${it.message}")
                    Flux.just("default1", "default2")
                }
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                //.expectError(RuntimeException::class.java)
                .expectNext("default1", "default2")
                .verifyComplete()
        //.verify()
    }

    @DisplayName("에러 반환")
    @Test
    fun testOnErrorReturn() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .concatWith(Flux.just("E"))
                .onErrorReturn("default")
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete()
    }

    @DisplayName("에러 맵")
    @Test
    fun testErrorMap() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .concatWith(Flux.just("E"))
                .onErrorMap { CustomException(it.message!!, it) }
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException::class.java)
                .verify()
    }

    @DisplayName("에러맵 재시도")
    @Test
    fun testErrorMap_With_Retry() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .concatWith(Flux.just("E"))
                .onErrorMap { CustomException(it.message!!, it) }
                .retry(2)
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException::class.java)
                .verify()
    }

    @DisplayName("에러맵 재시도 백오프")
    @Test
    fun testErrorMapRetryBackoff() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Error occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap { CustomException(it.message!!, it) }
                .retryExponentialBackoff(2, Duration.ofSeconds(15))
                .log()

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException::class.java)
                .verify()
    }
}

class CustomException(
        message: String,
        throwable: Throwable
) : RuntimeException(message, throwable)