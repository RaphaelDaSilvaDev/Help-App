package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class UserUpdateForm (
    @field:NotEmpty
    val name: String,

    @field:NotEmpty
    @field:Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres")
    val password: String
)
