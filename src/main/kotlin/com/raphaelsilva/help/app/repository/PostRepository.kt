package com.raphaelsilva.help.app.repository

import com.raphaelsilva.help.app.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {}