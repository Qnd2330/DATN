package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Integer> {
    Question findQuestionByQuestionText(String content);
}
