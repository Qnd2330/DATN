package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.response.SurveyQuestionAnswerResponse;
import com.vn.DATN.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepo extends JpaRepository<Survey, Integer> {
    Survey findSurveyByTitle(String content);

    @Query(value = QueryCommon.GET_SURVEY, nativeQuery = true)
    List<SurveyQuestionAnswerResponse> getSurveyDetail(@Param("surveyId") Integer surveyId);
}
