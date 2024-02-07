package com.raphaelsilva.help.app.mapper.answer

import com.raphaelsilva.help.app.dto.form.AnswerLikeForm
import com.raphaelsilva.help.app.mapper.SpecialMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.repository.UserRepository
import com.raphaelsilva.help.app.service.UserService
import org.springframework.stereotype.Controller

@Controller
class AnswerLikeFormMapper(private val userService: UserService): SpecialMapper<AnswerLikeForm, Answer, Answer> {
    override fun map(t: AnswerLikeForm, c: Answer): Answer {
        return Answer(
            id = c.id,
            message = c.message,
            author = c.author,
            post = c.post,
            answer = c.answer,
            isSolution = c.isSolution,
            createdAt = c.createdAt,
        )
    }
}