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
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody @Valid userForm: UserForm, uriBuilder: UriComponentsBuilder): ResponseEntity<UserView> {
        val createdUser = userService.create(userForm)
        val uri = uriBuilder.path("/api/users/${createdUser.id}").build().toUri()
        return ResponseEntity.created(uri).body(createdUser)
    }

    @GetMapping
    fun getAllUsers(): List<UserView> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserView {
        return userService.getUserById(id)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody @Valid userUpdateForm: UserUpdateForm
    ): ResponseEntity<UserView> {
        val updatedUser = userService.updateUser(id, userUpdateForm)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }

    @GetMapping("/like")
    fun getAllUserLikes(
        @RequestParam userId: Long,
        @RequestParam(required = false) postId: Long?,
        @RequestParam(required = false) answerId: Long?
    ): UserLikeView {
        return userService.getAllUserLikes(userId, postId, answerId)
    }
}