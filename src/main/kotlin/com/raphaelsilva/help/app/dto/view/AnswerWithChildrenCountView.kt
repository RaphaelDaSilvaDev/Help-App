package com.raphaelsilva.help.app.dto.view

import java.time.LocalDateTime

data class AnswerWithChildrenCountView(
    var id: Long,
    val message: String,
    val likes: Int,
    val authorId: Long,
    val postId: Long,
    var answersQuantity: Int?,
    val isSolution: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)