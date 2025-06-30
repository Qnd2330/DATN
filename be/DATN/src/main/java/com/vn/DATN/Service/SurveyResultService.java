package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.DTO.request.SurveyResultDetailDTO;
import com.vn.DATN.DTO.response.CompletedSurveyForMonitorAndTeacher;
import com.vn.DATN.DTO.response.CompletedSurveyForStudent;
import com.vn.DATN.DTO.response.SurveyCourseTeacherResponse;
import com.vn.DATN.DTO.response.SurveyResultResponse;
import com.vn.DATN.entity.SurveyResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyResultService {
    SurveyResult submitSurvey(SubmissionDTO submissionDTO);

    Page<SurveyCourseTeacherResponse> listUnfinishedPersonalReports(Integer userId, Pageable pageable);

    Page<SurveyResultResponse> listSurveyResultOfMonitor(Integer userId, Pageable pageable);

    Page<SurveyResultResponse> listSurveyResultOfTeacher(Integer userId, Pageable pageable);

    SurveyResultDetailDTO getSurveyResultDetailNested(Integer surveyResultId);

    Page<CompletedSurveyForStudent> findRatedSurveyResultsForStudent(Integer userId, Pageable pageable);

    Page<CompletedSurveyForMonitorAndTeacher> findRatedSurveyResultsForMonitorAndStudent(Integer userId, Pageable pageable);

    SurveyResult get(Integer id);

    SurveyResult deleteSurvey(Integer id);
}
