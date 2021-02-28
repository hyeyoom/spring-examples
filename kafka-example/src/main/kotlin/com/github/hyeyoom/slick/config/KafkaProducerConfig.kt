package com.github.hyeyoom.slick.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig(
    @Value(value = "\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String
) {

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val configProps = mutableMapOf<String, Any>()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(factory: ProducerFactory<String, String>): KafkaTemplate<String, String> {
        return KafkaTemplate(factory)
    }
}