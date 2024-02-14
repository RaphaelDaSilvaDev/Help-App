package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.AnswerEditForm
import com.raphaelsilva.help.app.dto.form.AnswerForm
import com.raphaelsilva.help.app.dto.form.AnswerLikeForm
import com.raphaelsilva.help.app.dto.view.AnswerLikeView
import com.raphaelsilva.help.app.dto.view.AnswerSimpleView
import com.raphaelsilva.help.app.dto.view.AnswerWithChildren
import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenCountView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.answer.AnswerCompleteViewMapper
import com.raphaelsilva.help.app.mapper.answer.AnswerFormMapper
import com.raphaelsilva.help.app.mapper.answer.AnswerLikeViewMapper
import com.raphaelsilva.help.app.mapper.answer.AnswerSimpleViewMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.model.PostStatus
import com.raphaelsilva.help.app.repository.AnswerRepository
import io.jsonwebtoken.lang.Collections
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class AnswerService(
    private val answerRepository: AnswerRepository,
    private val postService: PostService,
    private val userService: UserService,
    private val answerFormMapper: AnswerFormMapper,
    private val answerLikeViewMapper: AnswerLikeViewMapper,
    private val answerWhitQuantityViewMapper: AnswerCompleteViewMapper,
    private val answerSimpleViewMapper: AnswerSimpleViewMapper,
    private val notFoundMessage: String = "Answer not found!",
) {
    fun create(answerForm: AnswerForm): AnswerWithChildrenCountView {
        val answer = answerFormMapper.map(answerForm)
        val children = answerForm.answerId?.let {
            getByIdPure(it)
        }
        answer.answer = children
        answerRepository.save(answer)
        answer.post?.id?.let { postService.changeStatus(PostStatus.NOT_SOLVED, it) }
        val answerWithChildren = AnswerWithChildren(answer, ArrayList())
        return answerWhitQuantityViewMapper.map(answerWithChildren)
    }

    fun getById(id: Long): AnswerWithChildrenCountView {
        val answer = answerRepository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        val answersChildren = getAllChildrenById(id)
        val answerWithChildren = AnswerWithChildren(answer, answersChildren)
        return answerWhitQuantityViewMapper.map(answerWithChildren)
    }

    fun getAllChildrenById(id: Long, pageable: Pageable? = null): List<AnswerSimpleView> {
        return answerRepository.findAllByAnswerId(id, pageable).stream().map { answer ->
            answerSimpleViewMapper.map(answer)
        }.collect(Collectors.toList())
    }

    fun getByIdPure(id: Long): Answer {
        return answerRepository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
    }

    fun getByPostId(postId: Long, pageable: Pageable): Page<AnswerWithChildrenCountView> {
        val answer = answerRepository.getAllByPostIdPageable(postId, pageable).map { answer ->
            val answersChildren = answer.id?.let { answerId -> getAllChildrenById(answerId) }
            answersChildren?.let { child ->
                val answersWhitChildren = AnswerWithChildren(answer, child)
                answerWhitQuantityViewMapper.map(answersWhitChildren)
            }
        }
        return answer
    }

    fun getAllByPostIdPure(postId: Long): List<AnswerWithChildrenCountView> {
        val answer = answerRepository.getAllByPostId(postId).stream().map { answer ->
            val answersChildren = answer.id?.let { answerId -> getAllChildrenById(answerId) }
            answersChildren?.let { child ->
                val answersWhitChildren = AnswerWithChildren(answer, child)
                answerWhitQuantityViewMapper.map(answersWhitChildren)
            }
        }.collect(Collectors.toList())
        return answer
    }

    fun getAnswerByAnswerFather(id: Long, pageable: Pageable): Page<AnswerWithChildrenCountView> {
        val answer = answerRepository.findAllByAnswerId(id, pageable).map { answer ->
            val answersChildren = answer.id?.let { answerId -> getAllChildrenById(answerId) }
            answersChildren?.let { child ->
                val answersWhitChildren = AnswerWithChildren(answer, child)
                answerWhitQuantityViewMapper.map(answersWhitChildren)
            }
        }
        return answer
    }

    fun getAnswerByAnswerFatherPure(id: Long): List<AnswerWithChildrenCountView> {
        val answer = answerRepository.findAllByAnswerId(id).stream().map { answer ->
            val answersChildren = answer.id?.let { answerId -> getAllChildrenById(answerId) }
            answersChildren?.let { child ->
                val answersWhitChildren = AnswerWithChildren(answer, child)
                answerWhitQuantityViewMapper.map(answersWhitChildren)
            }
        }.collect(Collectors.toList())
        return answer
    }

    fun likeController(answerLikeForm: AnswerLikeForm): AnswerLikeView? {
        val answer = getByIdPure(answerLikeForm.answerId)
        val user = userService.getUserByIdPure(answerLikeForm.authorId)
        val hasLike = answer.likes.find { u -> u.id == user.id }
        if (hasLike == null) {
            val updatedAnswer = Answer(
                id = answer.id,
                message = answer.message,
                likes = listOf(user),
                answer = answer.answer,
                author = answer.author,
                isSolution = answer.isSolution,
                createdAt = answer.createdAt,
                post = answer.post,
            )
            answerRepository.save(updatedAnswer)
            return answerLikeViewMapper.map(answerSimpleViewMapper.map(updatedAnswer), user)
        } else {
            answerRepository.deleteLike(answerLikeForm.authorId, answerLikeForm.answerId)
            return null
        }
    }

    fun delete(id: Long, username: String) {
        val user = userService.getUserByUsername(username)
        val answer = getByIdPure(id)
        if(answer.author?.id == user.id){
            getAnswerByAnswerFatherPure(id).stream().forEach { child ->
                answerRepository.deleteById(child.id)
            }.let {
                answerRepository.deleteById(id)
            }
        }else{
            throw Exception("You can`t delete this answer!")
        }
    }

    fun deleteByPost(id: Long) {
            getAnswerByAnswerFatherPure(id).stream().forEach { child ->
                answerRepository.deleteById(child.id)
            }.let {
                answerRepository.deleteById(id)
            }
    }

    fun updateById(id: Long, answerEditForm: AnswerEditForm, username: String): AnswerSimpleView {
        val user = userService.getUserByUsername(username)
        val answer = getByIdPure(id)

        if(answer.author?.id == user.id){
            val updatedAnswer = Answer(
                id = answer.id,
                message = answerEditForm.message,
                answer = answer.answer,
                post = answer.post,
                isSolution = answer.isSolution,
                likes = answer.likes,
                author = answer.author,
                createdAt = answer.createdAt
            )
            return answerSimpleViewMapper.map(answerRepository.save(updatedAnswer))
        }else{
            throw Exception("You can`t edit this answer!")
        }
    }
}
