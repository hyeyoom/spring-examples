package com.github.hyeyoom.springoauth2example.entity

enum class Role(
    private val key: String,
    private val title: String
) {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "사용자")
}