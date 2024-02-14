package com.raphaelsilva.help.app.controller

import com.raphaelsilva.help.app.dto.form.AnswerForm
import com.raphaelsilva.help.app.dto.form.AnswerLikeForm
import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenCountView
import com.raphaelsilva.help.app.dto.view.UserUnlikeView
import com.raphaelsilva.help.app.service.AnswerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/answer")
class AnswerController(private val answerService: AnswerService) {

    @PostMapping
    fun createAnswer(
        @RequestBody answerForm: AnswerForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<AnswerWithChildrenCountView> {
        val answer = answerService.create(answerForm)
        val uri = uriBuilder.path("/api/answer/${answer.id}").build().toUri()
        return ResponseEntity.created(uri).body(answer)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): AnswerWithChildrenCountView {
        return answerService.getById(id)
    }

    @GetMapping("/post/{postId}")
    fun getByPostId(
        @PageableDefault(size = 10) pageable: Pageable,
        @PathVariable postId: Long
    ): Page<AnswerWithChildrenCountView> {
        return answerService.getByPostId(postId, pageable)
    }

    @GetMapping("/father/{answerId}")
    fun getAnswerByAnswerFather(@PageableDefault(size = 10) pageable: Pageable ,@PathVariable answerId: Long):
            Page<AnswerWithChildrenCountView> {
        return answerService.getAnswerByAnswerFather(answerId, pageable)
    }

    @PutMapping("/like")
    fun addLike(@RequestBody answerLikeForm: AnswerLikeForm, uriBuilder: UriComponentsBuilder): Any {
        val like = answerService.likeController(answerLikeForm)
        if (like != null) {
            val uri = uriBuilder.path("/like").build().toUri()
            return ResponseEntity.created(uri).body(like)
        }
        return ResponseEntity.ok().body(
            UserUnlikeView(
                message = "Unliked", userId = answerLikeForm.authorId, answerId = answerLikeForm.answerId
            )
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAnswer(@PathVariable id: Long, @AuthenticationPrincipal username: String){
        answerService.delete(id, username)
    }
}