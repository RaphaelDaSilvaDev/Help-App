package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class AnswerForm (
    @field:NotEmpty
    val message: String,

    @field:NotNull
    val authorId: Long,

    @field:NotNull
    val postId: Long,

    val answerId: Long?
)
