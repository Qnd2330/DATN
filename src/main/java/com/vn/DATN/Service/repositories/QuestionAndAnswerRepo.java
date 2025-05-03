package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.QuestionAnswer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionAndAnswerRepo extends JpaRepository<QuestionAnswer, Integer> {
    @Query("SELECT qa.answer FROM QuestionAnswer qa WHERE qa.question.id = :questionId")
    List<Answer> findAnswersByQuestionId(@Param("questionId") Integer questionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM QuestionAnswer qa WHERE qa.question.id = :questionId")
    int deleteByQuestionId(@Param("questionId") Integer questionId);
}
