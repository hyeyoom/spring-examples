package com.github.hyeyoom.webfluxexample.fluxandmonoplayground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

@DisplayName("콜드/핫 퍼블리셔")
internal class ColdAndHotPublisherTest {
    
    @DisplayName("콜드 퍼블리셔")
    @Test
    fun testColdPublisher() {
        val strings = Flux.just("A", "B", "C", "D", "E", "F")
            .delayElements(Duration.ofSeconds(1))

        strings.subscribe { println("Subscriber1: $it") }
        Thread.sleep(2000)

        strings.subscribe { println("Subscriber2: $it") }
        Thread.sleep(4000)
    }

    @DisplayName("핫 퍼블리셔")
    @Test
    fun testHotPublisher() {
        val strings = Flux.just("A", "B", "C", "D", "E", "F")
            .delayElements(Duration.ofSeconds(1))

        val hot = strings.publish()
        hot.connect()

        hot.subscribe { println("Subscriber1: $it") }
        Thread.sleep(3000)

        hot.subscribe { println("Subscriber2: $it") }
        Thread.sleep(4000)
    }

}