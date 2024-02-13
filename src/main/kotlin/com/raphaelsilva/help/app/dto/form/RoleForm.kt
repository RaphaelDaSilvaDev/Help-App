package com.raphaelsilva.help.app.dto.form

import jakarta.validation.constraints.NotEmpty

data class RoleForm (
    @field:NotEmpty
    val name: String
)