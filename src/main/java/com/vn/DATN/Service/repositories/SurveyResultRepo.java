package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.response.SurveyCourseTeacherResponse;
import com.vn.DATN.entity.SurveyResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurveyResultRepo extends JpaRepository<SurveyResult, Integer> {
    @Query(value = QueryCommon.GET_LIST_SURVEY, nativeQuery = true)
    Page<SurveyCourseTeacherResponse> getSurveySummaries(@Param("userId") Integer userId, Pageable pageable);
}
