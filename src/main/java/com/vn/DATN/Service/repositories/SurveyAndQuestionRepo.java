package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.SurveyQuestion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyAndQuestionRepo extends JpaRepository<SurveyQuestion, Integer> {
    @Query("SELECT sq.question FROM SurveyQuestion sq WHERE sq.survey.id = :surveyId")
    List<Question> findQuestionsBySurveyId(@Param("surveyId") Integer surveyId);
    @Modifying
    @Transactional
    @Query("DELETE  FROM SurveyQuestion sq WHERE sq.survey.id = :surveyId")
    int deleteBySurveyId(@Param("surveyId") Integer surveyId);
}
