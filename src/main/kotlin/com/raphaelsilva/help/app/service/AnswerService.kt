package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.AnswerForm
import com.raphaelsilva.help.app.dto.view.AnswerSimpleView
import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenCountView
import com.raphaelsilva.help.app.dto.view.AnswerWithChildren
import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.answer.AnswerFormMapper
import com.raphaelsilva.help.app.mapper.answer.AnswerSimpleViewMapper
import com.raphaelsilva.help.app.mapper.answer.AnswerWithChildrenMapper
import com.raphaelsilva.help.app.mapper.answer.AnswerWithChildrenQuantityViewMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.repository.AnswerRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class AnswerService(
    private val answerRepository: AnswerRepository,
    private val answerFormMapper: AnswerFormMapper,
    private val answerWithChildrenMapper: AnswerWithChildrenMapper,
    private val answerWhitQuantityViewMapper: AnswerWithChildrenQuantityViewMapper,
    private val answerViewMapper: AnswerSimpleViewMapper,
    private val notFoundMessage: String = "Answer not found!"
) {
    fun create(answerForm: AnswerForm): AnswerWithChildrenCountView {
        val children = answerForm.answerId?.let {
            getByIdPure(it)
        }
        val answer = answerFormMapper.map(answerForm, c = children)
        answerRepository.save(answer)
        val answerWithChildren = AnswerWithChildren(answer, ArrayList())
        return answerWhitQuantityViewMapper.map(answerWithChildren)
    }

    fun getById(id: Long): AnswerWithChildrenCountView {
        val answer = answerRepository.findById(id).orElseThrow{ NotFoundException(notFoundMessage) }
        val answersChildren = getAllChildrenById(id)
        val answerWithChildren = AnswerWithChildren(answer, answersChildren)
        return answerWhitQuantityViewMapper.map(answerWithChildren)
    }

    fun getAllChildrenById(id: Long): List<AnswerSimpleView> {
        return answerRepository.getAllAnswerChildren(id).stream().map { answer ->
            answerViewMapper.map(answer) }.collect(Collectors.toList())
    }

    fun getByIdPure(id: Long): Answer {
        return answerRepository.findById(id).orElseThrow{ NotFoundException(notFoundMessage) }
//        return answerViewMapper.map(answer)
    }

    fun getByPostId(postId: Long): List<AnswerWithChildrenView> {
        val answer = answerRepository.getAllByPostId(postId).stream().map { answer ->
            val answersChildren = answer.id?.let { answerId -> getAllChildrenById(answerId) }
                answersChildren?.let { child ->
                    val answersWhitChildren = AnswerWithChildren(answer, child)
                    answerWithChildrenMapper.map(answersWhitChildren)
            }
        }.collect(Collectors.toList())
        return answer
    }

}
