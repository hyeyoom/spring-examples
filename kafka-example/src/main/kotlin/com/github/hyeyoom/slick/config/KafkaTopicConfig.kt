package com.github.hyeyoom.slick.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(
    @Value(value = "\${kafka.bootstrapAddress}")
    private val bootstrapAddress: String
) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = mutableMapOf<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }
}