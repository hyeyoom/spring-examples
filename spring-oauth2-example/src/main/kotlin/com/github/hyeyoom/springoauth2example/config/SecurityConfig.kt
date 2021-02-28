package com.github.hyeyoom.springoauth2example.config

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfig(
): WebSecurityConfigurerAdapter() {

}