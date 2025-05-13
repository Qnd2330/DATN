package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.entity.SurveyResult;

public interface SurveyResultService {
    SurveyResult submitSurvey (SubmissionDTO submissionDTO);

    SurveyResult get (Integer id);

    SurveyResult deleteSurvey(Integer id);
}
