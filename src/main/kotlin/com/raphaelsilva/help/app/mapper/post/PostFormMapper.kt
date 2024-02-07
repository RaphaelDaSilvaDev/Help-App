package com.raphaelsilva.help.app.mapper.post

import com.raphaelsilva.help.app.dto.form.PostForm
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.service.UserService
import org.springframework.stereotype.Component

@Component
class PostFormMapper(
    private val userService: UserService,
    ): Mapper<PostForm, Post> {
    override fun map(t: PostForm): Post {
        return Post(
            title = t.title,
            message = t.message,
            tags = t.tags,
            createdBy = userService.getUserByIdPure(t.createdBy)
        )
    }

}