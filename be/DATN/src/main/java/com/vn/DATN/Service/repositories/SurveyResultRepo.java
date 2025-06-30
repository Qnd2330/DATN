package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.response.*;
import com.vn.DATN.entity.SurveyResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyResultRepo extends JpaRepository<SurveyResult, Integer> {
    @Query(value = QueryCommon.GET_LIST_SURVEY, nativeQuery = true)
    Page<SurveyCourseTeacherResponse> getSurveySummaries(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = QueryCommon.GET_LIST_SURVEY_RESULT_OF_MONITOR, nativeQuery = true)
    Page<SurveyResultResponse> getSurveyResultOfMonitor(@Param("monitorId") Integer userId, Pageable pageable);

    @Query(value = QueryCommon.GET_LIST_SURVEY_RESULT_OF_TEACHER, nativeQuery = true)
    Page<SurveyResultResponse> getSurveyResultOfTeacher(@Param("teacherId") Integer userId, Pageable pageable);

    @Query(value = QueryCommon.GET_DETAIL_SURVEY_RESULT, nativeQuery = true)
    List<SurveyResultDetailResponse> getSurveyResultDetailNested(@Param("surveyResultId") Integer surveyResultId);

    @Query(value = QueryCommon.GET_DONE_SURVEY_RESULT_FOR_STUDENT, nativeQuery = true)
    Page<CompletedSurveyForStudent> findRatedSurveyResultsForStudent(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = QueryCommon.GET_DONE_SURVEY_RESULT_FOR_MONITOR_AND_TEACHER, nativeQuery = true)
    Page<CompletedSurveyForMonitorAndTeacher> findRatedSurveyResultsForMonitorAndTeacher(@Param("evaluatorId") Integer evaluatorId, Pageable pageable);
}
