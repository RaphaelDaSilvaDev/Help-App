package com.raphaelsilva.help.app.mapper.user

import com.raphaelsilva.help.app.dto.view.UserLikeView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.mapper.answer.AnswerSimpleViewMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.model.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserLikeViewMapper(private val answerSimpleViewMapper: AnswerSimpleViewMapper): Mapper<List<Answer>, UserLikeView> {
    override fun map(t: List<Answer>): UserLikeView {
        return UserLikeView(
            like = t.map { answer -> answerSimpleViewMapper.map(answer) }
        )
    }
}

