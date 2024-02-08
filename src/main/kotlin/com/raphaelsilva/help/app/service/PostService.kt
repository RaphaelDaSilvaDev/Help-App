package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.PostForm
import com.raphaelsilva.help.app.dto.view.PostView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.post.PostFormMapper
import com.raphaelsilva.help.app.mapper.post.PostViewMapper
import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.model.PostStatus
import com.raphaelsilva.help.app.repository.AnswerRepository
import com.raphaelsilva.help.app.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val answerRepository: AnswerRepository,
    private val postFormMapper: PostFormMapper,
    private val postViewMapper: PostViewMapper,
    private val notFoundMessage: String = "Post not found!"
) {
    fun create(postForm: PostForm): PostView {
        val post = postFormMapper.map(postForm)
        postRepository.save(post)
        return postViewMapper.map(post)
    }

    fun getAll(pageable: Pageable): Page<PostView> {
        val posts = postRepository.findAll(pageable).map { post -> postViewMapper.map(post) }
        posts.forEach { post ->
            post.answerQuantity = post.id?.let { answerRepository.getAllByPostId(it).size }
        }

        return posts
    }

    fun getById(id: Long): Post {
        return postRepository.findById(id).orElseThrow{ NotFoundException(notFoundMessage) }
    }

    fun changeStatus(status: PostStatus, postId: Long): Post{
        val post = getById(postId)
        post.status = status
        return postRepository.save(post)
    }


}
