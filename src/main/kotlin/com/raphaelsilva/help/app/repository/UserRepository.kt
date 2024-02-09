package com.raphaelsilva.help.app.repository

import com.raphaelsilva.help.app.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(username: String?): User?
}