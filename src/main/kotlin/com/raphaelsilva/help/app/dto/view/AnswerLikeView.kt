package com.raphaelsilva.help.app.dto.view

import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.model.User

data class AnswerLikeView(
    val answer: AnswerSimpleView,
    val user: UserView
)
