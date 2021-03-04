package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers.parallel
import reactor.test.StepVerifier

@DisplayName("변환 테스트")
internal class FluxAndMonoTransformTest {

    private val names = mutableListOf("adam", "anna", "jack", "jenny")

    @DisplayName("대문자로 변환")
    @Test
    fun testTransformUsingMap() {
        val flux = Flux.fromIterable(names)
            .map { it.toUpperCase() }
            .log()

        StepVerifier.create(flux)
            .expectNext("ADAM", "ANNA", "JACK", "JENNY")
            .verifyComplete()
    }

    @DisplayName("길이 변환")
    @Test
    fun testTransformUsingMapLength() {

        val flux = Flux.fromIterable(names)
            .map { it.length }
            .log()

        StepVerifier.create(flux)
            .expectNext(4, 4, 4, 5)
            .verifyComplete()
    }

    @DisplayName("필터 + 변환")
    @Test
    fun testCombination() {
        val flux = Flux.fromIterable(names)
            .filter { it.length > 4 }
            .map { it.toUpperCase() }
            .log()

        StepVerifier.create(flux)
            .expectNext("JENNY")
            .verifyComplete()
    }

    @DisplayName("flatmap - slower")
    @Test
    fun testFlatmapSlowVersion() {
        val flux = Flux.fromIterable(mutableListOf("A", "B", "C", "D", "E", "F"))
            .flatMap { Flux.fromIterable(convertToList(it)) }
            .log()

        StepVerifier.create(flux)
            .expectNextCount(12)
            .verifyComplete()
    }

    private fun convertToList(it: String): MutableList<String> {
        try {
            Thread.sleep(1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mutableListOf(it, "newValue")
    }

    @DisplayName("flatmap - faster")
    @Test
    fun testFlatmapFasterVersion() {
        val t1 = System.currentTimeMillis()
        val flux = Flux.fromIterable(mutableListOf("A", "B", "C", "D", "E", "F"))
            .window(1)
            .flatMap {
                it.map(this::convertToList).subscribeOn(parallel())
                    .flatMap { a -> Flux.fromIterable(a) }
            }
            .log()

        StepVerifier.create(flux)
            .expectNextCount(12)
            .verifyComplete()
        val t2 = System.currentTimeMillis()
        println(t2 - t1)
    }
}