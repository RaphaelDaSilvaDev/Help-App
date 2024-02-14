package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotEmpty

data class PostEditForm (
    val title: String?,

    val message: String?,

    val tags: List<String>?,
)