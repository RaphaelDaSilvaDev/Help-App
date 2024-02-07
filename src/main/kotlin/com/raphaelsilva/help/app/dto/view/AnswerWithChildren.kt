package com.raphaelsilva.help.app.dto.view

import com.raphaelsilva.help.app.model.Answer

data class AnswerWithChildren (
    val answer: Answer,
    val children: List<AnswerSimpleView>
)