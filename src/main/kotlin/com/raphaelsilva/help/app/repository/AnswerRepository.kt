package com.raphaelsilva.help.app.repository

import com.raphaelsilva.help.app.model.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AnswerRepository: JpaRepository<Answer, Long> {
    @Query(value = "SELECT * FROM Answer WHERE answer_id = ?1", nativeQuery = true)
    fun getAllAnswerChildren(id: Long): List<Answer>

    @Query(value = "SELECT * FROM Answer WHERE post_id = ?1 AND answer_id IS NULL", nativeQuery = true)
    fun getAllByPostId(postId: Long): List<Answer>
}