package com.github.hyeyoom.springoauth2example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class SpringOauth2ExampleApplication

fun main(args: Array<String>) {
    runApplication<SpringOauth2ExampleApplication>(*args)
}
