package com.github.hyeyoom.springoauth2example.entity.dto

import com.github.hyeyoom.springoauth2example.entity.User
import java.io.Serializable

data class SessionUser(
    private val name: String,
    private val email: String,
    private val picture: String
) : Serializable {
    constructor(user: User) : this(user.name, user.email, user.picture)
}