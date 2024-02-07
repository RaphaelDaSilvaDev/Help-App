package com.raphaelsilva.help.app.dto.view

import org.apache.catalina.User
import java.time.LocalDateTime

data class PostView (
    val id: Long?,
    val title: String,
    val tags: List<String>,
    val createdAt: LocalDateTime,
    val createdBy: UserView
)
