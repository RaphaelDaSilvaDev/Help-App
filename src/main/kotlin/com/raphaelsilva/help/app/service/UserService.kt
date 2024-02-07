package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.UserForm
import com.raphaelsilva.help.app.dto.form.UserLikeAnswer
import com.raphaelsilva.help.app.dto.form.UserUpdateForm
import com.raphaelsilva.help.app.dto.view.UserLikeView
import com.raphaelsilva.help.app.dto.view.UserView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.user.UserFormMapper
import com.raphaelsilva.help.app.mapper.user.UserLikeViewMapper
import com.raphaelsilva.help.app.mapper.user.UserViewMapper
import com.raphaelsilva.help.app.model.User
import com.raphaelsilva.help.app.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService(
        private var userRepository: UserRepository,
        private val userFormMapper: UserFormMapper,
        private val userViewMapper: UserViewMapper,
        private val userLikeViewMapper: UserLikeViewMapper,
        private val notFoundMessage: String = "User not found!"
) {
    fun create(userForm: UserForm): UserView {
        val user = userFormMapper.map(userForm)
        userRepository.save(user)
        return userViewMapper.map(user)
    }

    fun getAllUsers(): List<UserView> {
        return userRepository.findAll().stream().map { user -> userViewMapper.map(user) }.collect(Collectors.toList())
    }

    fun getUserById(id: Long): UserView {
        val user = userRepository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        return userViewMapper.map(user)
    }

    fun getUserByIdPure(id: Long): User {
        return userRepository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
    }

    fun updateUser(id: Long, userUpdateForm: UserUpdateForm): UserView {
        val user = userRepository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        user.name = userUpdateForm.name
        user.password = userUpdateForm.password
        userRepository.save(user)
        return userViewMapper.map(user)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun getAllUserLikes(id: Long): UserLikeView {
        val userLikes = userRepository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        return userLikeViewMapper.map(userLikes.likedAnswers)
    }

    fun getUserLikesByPost(userId: Long, postId: Long): UserLikeView{
        val userLikes = userRepository.findById(userId).orElseThrow{NotFoundException(notFoundMessage)}
        val userLikesPost = userLikes.likedAnswers.filter { answer ->  answer.post?.id == postId && answer.answer?.id== null }
        return userLikeViewMapper.map(userLikesPost)
    }

    fun getUserLikeAnswerChild(userLikeAnswer: UserLikeAnswer): UserLikeView{
        val userLikes = userRepository.findById(userLikeAnswer.userId).orElseThrow{NotFoundException(notFoundMessage)}
        val userLikesPost = userLikes.likedAnswers.filter { answer ->  answer.post?.id == userLikeAnswer.postId &&
                answer.answer?.id==userLikeAnswer.answerId}
        return userLikeViewMapper.map(userLikesPost)
    }
}