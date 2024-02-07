package com.raphaelsilva.help.app.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Answer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val message: String = "",

    @ManyToMany
    @JoinTable(
        name = "answer_like",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "answer_id")])
    var likes: List<User> = ArrayList(),

    @ManyToOne
    val author: User? = null,

    @ManyToOne
    val post: Post? = null,

    @ManyToOne
    var answer: Answer? = null,

    @Column(nullable = false)
    val isSolution: Boolean = false,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
