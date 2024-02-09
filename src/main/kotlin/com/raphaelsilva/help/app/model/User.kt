package com.raphaelsilva.help.app.model

import jakarta.persistence.*

@Entity(name = "users")
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(nullable = false)
        var name: String = "",

        @Column(nullable = false, unique = true)
        val email: String = "",

        @Column(nullable = false)
        var password: String = "",

        @ManyToMany(mappedBy = "likes")
        val likedAnswers: List<Answer> = ArrayList(),

        @Enumerated(value = EnumType.STRING)
        val role: Roles = Roles.USER
)
