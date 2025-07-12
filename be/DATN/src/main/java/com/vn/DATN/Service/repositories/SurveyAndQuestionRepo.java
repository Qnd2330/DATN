package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Survey;
import com.vn.DATN.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyAndQuestionRepo extends JpaRepository<SurveyQuestion, Integer> {
    List<SurveyQuestion> findBySurvey(Survey survey);
}
