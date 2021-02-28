package com.github.hyeyoom.springoauth2example.config

import com.github.hyeyoom.springoauth2example.entity.Role
import com.github.hyeyoom.springoauth2example.entity.User

data class OAuthAttributes(
    val attributes: MutableMap<String, Any> = mutableMapOf(),
    val nameAttributeKey: String,
    val name: String,
    val email: String,
    val picture: String
) {

    fun toEntity(): User {
        return User(
            name = name,
            email = email,
            picture = picture,
            role = Role.GUEST
        )
    }

    companion object {
        fun of(registrationId: String, userNameAttributeName: String, attributes: MutableMap<String, Any>):
                OAuthAttributes {
            return OAuthAttributes(
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                picture = attributes["picture"] as String,
                attributes = attributes,
                nameAttributeKey = userNameAttributeName
            )
        }
    }
}