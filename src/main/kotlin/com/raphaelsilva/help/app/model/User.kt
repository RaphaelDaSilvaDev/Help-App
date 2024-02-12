package com.raphaelsilva.help.app.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

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

        @JsonIgnore
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
            name = "users_role",
            joinColumns = [JoinColumn(name = "users_id")],
            inverseJoinColumns = [JoinColumn(name="role_id")])
        val role: List<Roles> = mutableListOf()
)
