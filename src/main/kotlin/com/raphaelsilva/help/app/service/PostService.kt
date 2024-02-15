package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.PostEditForm
import com.raphaelsilva.help.app.dto.form.PostForm
import com.raphaelsilva.help.app.dto.view.PostView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.post.PostFormMapper
import com.raphaelsilva.help.app.mapper.post.PostViewMapper
import com.raphaelsilva.help.app.model.Answer
import com.raphaelsilva.help.app.model.Post
import com.raphaelsilva.help.app.model.PostStatus
import com.raphaelsilva.help.app.repository.AnswerRepository
import com.raphaelsilva.help.app.repository.PostRepository
import com.raphaelsilva.help.app.repository.UserRepository
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    @Lazy private val answerService: AnswerService,
    private val roleService: RoleService,
    private val postFormMapper: PostFormMapper,
    private val postViewMapper: PostViewMapper,
    private val notFoundMessage: String = "Post not found!", private val userRepository: UserRepository
) {
    fun create(postForm: PostForm): PostView {
        val post = postFormMapper.map(postForm)
        postRepository.save(post)
        return postViewMapper.map(post)
    }

    fun getAll(pageable: Pageable): Page<PostView> {
        val posts = postRepository.findAll(pageable).map { post -> postViewMapper.map(post) }
        posts.forEach { post ->
            post.answerQuantity = post.id?.let { answerService.getAllByPostIdPure(it).size }
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

    fun delete(id: Long, username: String) {
        val user = userRepository.findByEmail(username)
        val adminRole = roleService.getRoleByNamePure("ADMIN")
        val post = getById(id)
        if(post.createdBy?.id == user?.id || user?.role?.contains(adminRole) == true){
            answerService.getAllByPostIdPure(id).stream().forEach { answer ->
                answerService.deleteByPost(answer.id)
            }.let {
                postRepository.deleteById(id)
            }
        }else{
            throw Exception("You can`t delete this post!")
        }
    }

    fun updateById(id: Long, postEditForm: PostEditForm, username: String): PostView {
        val user = userRepository.findByEmail(username)
        val post = getById(id)

        if(post.createdBy?.id == user?.id){
            val updatedPost = Post(
                id = post.id,
                title = postEditForm.title ?: post.title,
                message = postEditForm.message ?: post.message,
                tags = postEditForm.tags ?: post.tags,
                status = post.status,
                createdAt = post.createdAt,
                createdBy = post.createdBy
            )

            return postViewMapper.map(postRepository.save(updatedPost))
        }else{
            throw Exception("You can`t edit this post!")
        }
    }


}
