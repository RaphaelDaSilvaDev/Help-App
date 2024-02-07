package com.raphaelsilva.help.app.controller

import com.raphaelsilva.help.app.dto.form.UserForm
import com.raphaelsilva.help.app.dto.form.UserLikeAnswer
import com.raphaelsilva.help.app.dto.form.UserUpdateForm
import com.raphaelsilva.help.app.dto.view.UserLikeView
import com.raphaelsilva.help.app.dto.view.UserView
import com.raphaelsilva.help.app.service.UserService
import jakarta.validation.Valid
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
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody @Valid userForm: UserForm, uriBuilder: UriComponentsBuilder): ResponseEntity<UserView>{
        val createdUser = userService.create(userForm)
        val uri = uriBuilder.path("/api/users/${createdUser.id}").build().toUri()
        return ResponseEntity.created(uri).body(createdUser)
    }

    @GetMapping
    fun getAllUsers(): List<UserView>{
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserView{
        return userService.getUserById(id)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody @Valid userUpdateForm: UserUpdateForm):
            ResponseEntity<UserView>{
        val updatedUser = userService.updateUser(id, userUpdateForm)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long){
        userService.deleteUser(id)
    }

    @GetMapping("/like/{id}")
    fun getAllUserLikes(@PathVariable id: Long): UserLikeView{
        return userService.getAllUserLikes(id)
    }

    @GetMapping("/like/post/{id}/{postId}")
    fun getUserLikesByPost(@PathVariable id: Long, @PathVariable postId: Long): UserLikeView{
        return userService.getUserLikesByPost(id, postId)
    }

    @GetMapping("/like/answer")
    fun getUserLikesByAnswer(@RequestBody userLikeAnswer: UserLikeAnswer): UserLikeView{
        return userService.getUserLikeAnswerChild(userLikeAnswer)
    }
}