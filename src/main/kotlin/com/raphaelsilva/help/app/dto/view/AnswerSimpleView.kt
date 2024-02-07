package com.raphaelsilva.help.app.dto.view

import java.time.LocalDateTime

data class AnswerSimpleView (
    var id: Long,
    val message: String,
    val likes: Int,
    val authorId: Long,
    val postId: Long,
    val isSolution: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)