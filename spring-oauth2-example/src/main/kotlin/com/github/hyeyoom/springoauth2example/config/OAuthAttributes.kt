package com.github.hyeyoom.springoauth2example.config

data class OAuthAttributes(
    private val attributes: MutableMap<String, Any> = mutableMapOf(),
    private val nameAttributeKey: String,
    private val name: String,
    private val email: String,
    private val picture: String
) {
    companion object {
        fun of(registrationId: String, userNameAttributeName: String, attributes: MutableMap<String, Any>):
                OAuthAttributes {
        }
    }
}