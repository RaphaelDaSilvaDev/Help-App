package com.raphaelsilva.help.app.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Answer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val message: String = "",

    @Column(nullable = false)
    val likes: Int = 0,

    @ManyToOne
    val author: User? = null,

    @ManyToOne
    val post: Post? = null,


    @ManyToOne
    val answer: Answer? = null,

    @Column(nullable = false)
    val isSolution: Boolean = false,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
