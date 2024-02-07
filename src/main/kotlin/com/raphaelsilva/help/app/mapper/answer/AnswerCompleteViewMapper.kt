package com.raphaelsilva.help.app.mapper.answer

import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenCountView
import com.raphaelsilva.help.app.dto.view.AnswerWithChildren
import com.raphaelsilva.help.app.mapper.Mapper
import org.springframework.stereotype.Controller

@Controller
class AnswerCompleteViewMapper: Mapper<AnswerWithChildren, AnswerWithChildrenCountView> {
    override fun map(t: AnswerWithChildren): AnswerWithChildrenCountView {
        return AnswerWithChildrenCountView(
            id = t.answer.id!!,
            message = t.answer.message,
            likes = t.answer.likes.size,
            postId = t.answer.post?.id!!,
            authorId = t.answer.author?.id!!,
            isSolution = t.answer.isSolution,
            createdAt = t.answer.createdAt,
            answersQuantity = t.children.size
        )
    }
}