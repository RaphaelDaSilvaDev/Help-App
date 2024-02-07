package com.raphaelsilva.help.app.model

import com.raphaelsilva.help.app.dto.view.UserView
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false)
    val message: String = "",

    @Column(nullable = false)
    val tags: List<String> = ArrayList(),

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    val createdBy: User? = null,

    @Enumerated(value = EnumType.STRING)
    val status: PostStatus = PostStatus.NOT_ANSWER
)
