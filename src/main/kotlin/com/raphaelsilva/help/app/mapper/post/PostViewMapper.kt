package com.raphaelsilva.help.app.mapper.post

import com.raphaelsilva.help.app.dto.view.PostView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.service.UserService
import org.springframework.stereotype.Controller

@Controller
class PostViewMapper(
    private val userService: UserService
): Mapper<Post, PostView> {
    override fun map(t: Post): PostView {
        return PostView(
            id = t.id,
            title = t.title,
            tags = t.tags,
            createdBy = userService.getUserById(t.id!!),
            createdAt = t.createdAt
        )
    }
}