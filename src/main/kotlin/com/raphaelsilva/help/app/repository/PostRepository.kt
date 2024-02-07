package com.raphaelsilva.help.app.repository

import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.model.PostStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository: JpaRepository<Post, Long> {
}