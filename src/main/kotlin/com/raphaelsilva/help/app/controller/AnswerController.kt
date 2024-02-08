package com.raphaelsilva.help.app.controller

import com.raphaelsilva.help.app.dto.form.AnswerForm
import com.raphaelsilva.help.app.dto.form.AnswerLikeForm
import com.raphaelsilva.help.app.dto.view.AnswerLikeView
import com.raphaelsilva.help.app.dto.view.AnswerWithChildrenCountView
import com.raphaelsilva.help.app.dto.view.UserUnlikeView
import com.raphaelsilva.help.app.service.AnswerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/answer")
class AnswerController(private val answerService: AnswerService) {

    @PostMapping
    fun createAnswer(@RequestBody answerForm: AnswerForm, uriBuilder: UriComponentsBuilder): ResponseEntity<AnswerWithChildrenCountView>{
        val answer = answerService.create(answerForm)
        val uri = uriBuilder.path("/api/answer/${answer.id}").build().toUri()
        return ResponseEntity.created(uri).body(answer)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): AnswerWithChildrenCountView{
        return answerService.getById(id)
    }

    @GetMapping("/post/{postId}")
    fun getByPostId(@PathVariable postId: Long): List<AnswerWithChildrenCountView>{
        return answerService.getByPostId(postId)
    }

    @GetMapping("/father/{answerId}")
    fun getAnswerByAnswerFather(@PathVariable answerId: Long): List<AnswerWithChildrenCountView>{
        return answerService.getAnswerByAnswerFather(answerId)
    }

    @PutMapping("/like")
    fun addLike(@RequestBody answerLikeForm: AnswerLikeForm, uriBuilder: UriComponentsBuilder): Any {
        val like = answerService.likeController(answerLikeForm)
        if(like != null){
            val uri = uriBuilder.path("/like").build().toUri()
            return ResponseEntity.created(uri).body(like)
        }
        return ResponseEntity.ok().body(UserUnlikeView(message = "Unliked", userId = answerLikeForm.authorId,
                                                       answerId = answerLikeForm.answerId))
    }
}