package com.raphaelsilva.help.app.controller

import com.raphaelsilva.help.app.dto.form.PostForm
import com.raphaelsilva.help.app.dto.view.PostView
import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.service.PostService
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
@RequestMapping("/api/post")
class PostController(private val postService: PostService) {

    @PostMapping
    fun createPost(@RequestBody postForm: PostForm, uriBuilder: UriComponentsBuilder): ResponseEntity<PostView> {
        val post = postService.create(postForm)
        val uri = uriBuilder.path("/api/post/${post.id}").build().toUri()
        return ResponseEntity.created(uri).body(post)
    }

    @GetMapping
    fun getAllPost(@PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable:
                   Pageable):
            Page<PostView> {
        return postService.getAll(pageable)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): Post {
        return postService.getById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long, @AuthenticationPrincipal username: String){
        postService.delete(id, username)
    }
}