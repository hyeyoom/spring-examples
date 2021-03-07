package com.github.hyeyoom.webfluxexample.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
@WebFluxTest
@DisplayName("플럭스/모노 컨트롤러 테스트")
internal class FluxAndMonoControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @DisplayName("flux approach 1")
    @Test
    fun testFluxApproach1() {

        val ints: Flux<Int> = webTestClient.get().uri("/flux")
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


    @DisplayName("flux approach 2")
    @Test
    fun testFluxApproach2() {

        webTestClient.get().uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Int::class.java)
            .hasSize(4)

    }

    @DisplayName("flux approach 3")
    @Test
    fun testFluxApproach3() {

        val expectedIntsList = mutableListOf(1, 2, 3, 4)

        val entityExchangeResult: EntityExchangeResult<MutableList<Int>> = webTestClient
            .get().uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Int::class.java)
            .returnResult()

        assertEquals(expectedIntsList, entityExchangeResult.responseBody)
    }

    @DisplayName("flux approach 4")
    @Test
    fun testFluxApproach4() {

        val expectedIntsList = mutableListOf(1, 2, 3, 4)

        webTestClient
            .get().uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(MutableList::class.java)
        //  .consumeWith<Nothing> {
        // 코틀린에선 맛탱이 가는 이슈 있음
        // https://discuss.kotlinlang.org/t/type-interference-issue-with-the-webflux-webtestclient-and-kotlin/3880/2
//            }

    }

    @DisplayName("flux stream")
    @Test
    fun fluxStream() {

        val longStreamFlux: Flux<Long> = webTestClient.get().uri("/flux-stream")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .expectStatus().isOk
            .returnResult(Long::class.java)
            .responseBody

        StepVerifier.create(longStreamFlux)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .thenCancel()
            .verify()
    }

    @DisplayName("mono")
    @Test
    fun testMono() {

        val body = webTestClient.get().uri("/mono")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBody(Int::class.java)
    }
}