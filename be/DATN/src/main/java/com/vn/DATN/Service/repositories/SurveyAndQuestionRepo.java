package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAndQuestionRepo extends JpaRepository<SurveyQuestion, Integer> {
}
