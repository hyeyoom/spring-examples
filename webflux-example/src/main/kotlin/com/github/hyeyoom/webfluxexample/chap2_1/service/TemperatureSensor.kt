package com.github.hyeyoom.webfluxexample.chap2_1.service

import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

private val log = KotlinLogging.logger {}

@Service
class TemperatureSensor(
    private val publisher: ApplicationEventPublisher,
) {
    private val rnd: Random = Random()
    private val executor = Executors.newSingleThreadScheduledExecutor()

    @PostConstruct
    fun startProcessing() {
        executor.schedule(this::probe, 1, TimeUnit.SECONDS)
    }

    private fun probe() {
        val temp = 16 + rnd.nextGaussian() * 10
        publisher.publishEvent(Temperature(temp))
        executor.schedule(this::probe, rnd.nextInt(5000).toLong(), TimeUnit.MILLISECONDS)
    }
}