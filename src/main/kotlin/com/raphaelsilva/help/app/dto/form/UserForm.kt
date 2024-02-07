package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class UserForm(
    @field:NotEmpty
    val name: String,

    @field:NotEmpty
    @field:Email(message = "Digite um email válido")
    val email: String,

    @field:NotEmpty
    @field:Size(min = 6, message = "A senha deve conter pelo menos 6 caracteres")
    val password: String
)
