package com.github.hyeyoom.webfluxexample.handler

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension::class)
@DisplayName("핸들러 함수 테스트")
internal class SampleHandlerFunctionTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @DisplayName("flux approach 1")
    @Test
    fun testFluxApproach1() {

        val ints: Flux<Int> = webTestClient.get().uri("/functional/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .returnResult(Int::class.java)
            .responseBody

        StepVerifier.create(ints)
            .expectSubscription()
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .expectNext(4)
            .verifyComplete()
    }

    @DisplayName("mono test")
    @Test
    fun testMono() {
        val result = webTestClient.get().uri("/functional/mono")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBody(Int::class.java)
            .returnResult()
            .responseBody
        assertEquals(1, result)
    }

}