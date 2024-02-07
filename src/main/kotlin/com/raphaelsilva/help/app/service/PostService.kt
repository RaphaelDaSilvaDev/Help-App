package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.PostForm
import com.raphaelsilva.help.app.dto.view.PostView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.post.PostFormMapper
import com.raphaelsilva.help.app.mapper.post.PostViewMapper
import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.repository.PostRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class PostService(
    private var postRepository: PostRepository,
    private val postFormMapper: PostFormMapper,
    private val postViewMapper: PostViewMapper,
    private val notFoundMessage: String = "Post not found!"
) {
    fun create(postForm: PostForm): Post {
        val post = postFormMapper.map(postForm)
        postRepository.save(post)
        return post
    }

    fun getAll(): List<PostView> {
        return postRepository.findAll().stream().map { post -> postViewMapper.map(post) }.collect(Collectors.toList())
    }

    fun getById(id: Long): Post {
        return postRepository.findById(id).orElseThrow{ NotFoundException(notFoundMessage) }
    }


}
