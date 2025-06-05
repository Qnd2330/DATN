package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.DTO.response.SurveyCourseTeacherResponse;
import com.vn.DATN.entity.SurveyResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyResultService {
    SurveyResult submitSurvey (SubmissionDTO submissionDTO);

    Page<SurveyCourseTeacherResponse> getSurveyWithPagination(Integer userId, Pageable pageable);

    SurveyResult get (Integer id);

    SurveyResult deleteSurvey(Integer id);
}
