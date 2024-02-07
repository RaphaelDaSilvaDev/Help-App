package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.apache.catalina.User

data class PostForm (
    @field:NotEmpty
    val title: String,

    @field:NotEmpty
    val message: String,

    @field:NotEmpty
    val tags: List<String>,

    @field:NotEmpty
    val createdBy: Long,
)
