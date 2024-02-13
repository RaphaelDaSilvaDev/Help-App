package com.raphaelsilva.help.app.repository

import com.raphaelsilva.help.app.model.Roles
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoleRepository: JpaRepository<Roles, Long>  {
    fun findByName(name: String): Roles
}
