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
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService(
        private var userRepository: UserRepository,
        private val userFormMapper: UserFormMapper,
        private val userViewMapper: UserViewMapper,
        private val userLikeViewMapper: UserLikeViewMapper,
        private val notFoundMessage: String = "User not found!"
): UserDetailsService {
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

    fun deleteUser(id: Long, username: String) {
        val user = getUserById(id)
        if(user.email == username){
            userRepository.deleteById(id)
        }else{
            throw Exception("You can`t delete this user")
        }
    }

    fun getAllUserLikes(userId: Long, postId: Long?, answerId: Long?): UserLikeView {
        val userLikes = userRepository.findById(userId).orElseThrow{NotFoundException(notFoundMessage)}
        if(postId != null && answerId != null){
            val userLikesPost = userLikes.likedAnswers.filter { answer ->  answer.post?.id == postId &&
                    answer.answer?.id==answerId}
            return userLikeViewMapper.map(userLikesPost)
        }else if(postId != null){
            val userLikesPost = userLikes.likedAnswers.filter { answer ->  answer.post?.id == postId && answer.answer?.id== null }
            return userLikeViewMapper.map(userLikesPost)
        }
        return userLikeViewMapper.map(userLikes.likedAnswers)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw NotFoundException(notFoundMessage)
        return UserDetail(user)
    }
}