package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {
    Answer findByContent(String content);
}
