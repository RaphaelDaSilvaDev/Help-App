package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotNull

data class AnswerLikeForm(
    @field:NotNull
    val authorId: Long,

    @field:NotNull
    val answerId: Long
)
