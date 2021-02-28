package com.github.hyeyoom.springoauth2example.entity

import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    var picture: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role
) : BaseTimeEntity()