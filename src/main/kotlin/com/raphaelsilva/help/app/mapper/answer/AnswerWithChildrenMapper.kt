package com.raphaelsilva.help.app.mapper.answer

import com.raphaelsilva.help.app.dto.view.AnswerWithChildren
import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenView
import com.raphaelsilva.help.app.mapper.Mapper
import org.springframework.stereotype.Component

@Component
class AnswerWithChildrenMapper: Mapper<AnswerWithChildren, AnswerWithChildrenView> {
    override fun map(t: AnswerWithChildren): AnswerWithChildrenView {
        return AnswerWithChildrenView(
            id = t.answer.id!!,
            message = t.answer.message,
            likes = t.answer.likes,
            postId = t.answer.post?.id!!,
            authorId = t.answer.author?.id!!,
            isSolution = t.answer.isSolution,
            createdAt = t.answer.createdAt,
            answers = t.children
        )
    }
}