package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotEmpty

data class AnswerEditForm (
    @field:NotEmpty
    val message: String,
)