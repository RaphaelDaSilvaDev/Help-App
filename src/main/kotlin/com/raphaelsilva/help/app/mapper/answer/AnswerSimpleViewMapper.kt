package com.raphaelsilva.help.app.mapper.answer

import com.raphaelsilva.help.app.dto.view.AnswerSimpleView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.model.Answer
import org.springframework.stereotype.Controller

@Controller
class AnswerSimpleViewMapper: Mapper<Answer, AnswerSimpleView> {
    override fun map(t: Answer): AnswerSimpleView {
        return AnswerSimpleView(
            id = t.id!!,
            message = t.message,
            likes = t.likes.size,
            postId = t.post?.id!!,
            authorId = t.author?.id!!,
            isSolution = t.isSolution,
            createdAt = t.createdAt
        )
    }
}