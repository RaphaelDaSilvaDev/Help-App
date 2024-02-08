package com.raphaelsilva.help.app.repository

import com.raphaelsilva.help.app.model.Answer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AnswerRepository: JpaRepository<Answer, Long> {
    fun findAllByAnswerId(id: Long): List<Answer>

    @Query(value = "SELECT * FROM Answer WHERE post_id = ?1 AND answer_id IS NULL", nativeQuery = true)
    fun getAllByPostId(postId: Long): List<Answer>

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query(value = "DELETE FROM answer_like WHERE user_id = ?1 AND answer_id = ?2", nativeQuery = true)
    fun deleteLike(userId: Long, answerId: Long)
}