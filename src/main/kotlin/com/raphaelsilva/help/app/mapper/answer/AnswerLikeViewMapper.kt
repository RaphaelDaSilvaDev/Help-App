package com.raphaelsilva.help.app.mapper.answer

import com.raphaelsilva.help.app.dto.view.AnswerLikeView
import com.raphaelsilva.help.app.dto.view.AnswerSimpleView
import com.raphaelsilva.help.app.dto.view.UserView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.mapper.SpecialMapper
import com.raphaelsilva.help.app.mapper.user.UserViewMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.model.User
import org.springframework.stereotype.Controller

@Controller
class AnswerLikeViewMapper(private val userViewMapper: UserViewMapper): SpecialMapper<AnswerSimpleView, User, AnswerLikeView> {
    override fun map(t: AnswerSimpleView, c: User): AnswerLikeView {
        return AnswerLikeView(
            answer = t,
            user = userViewMapper.map(c)
        )
    }
}