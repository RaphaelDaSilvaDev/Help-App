package com.raphaelsilva.help.app.mapper.answer

import com.raphaelsilva.help.app.dto.form.AnswerForm
import com.raphaelsilva.help.app.dto.view.AnswerSimpleView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.mapper.SpecialMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.service.AnswerService
import com.raphaelsilva.help.app.service.PostService
import com.raphaelsilva.help.app.service.UserService
import org.springframework.stereotype.Component

@Component
class AnswerFormMapper(
    private val userService: UserService,
    private val postService: PostService,
): Mapper<AnswerForm, Answer> {
    override fun map(t: AnswerForm): Answer {
        return Answer(
            message = t.message,
            author = userService.getUserByIdPure(t.authorId),
            post = postService.getById(t.postId),
        )
    }

}