package com.vn.DATN.Service.repositories;

import com.vn.DATN.DTO.response.CompletedSurveyStudentResponse;
import com.vn.DATN.entity.DummyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompletedSurveyStudentRepo extends JpaRepository<DummyEntity, Long> {
    @Query(value = com.vn.DATN.Common.QueryCommon.GET_COMPLETED_SURVEY_STUDENTS, nativeQuery = true)
    List<CompletedSurveyStudentResponse> getCompletedSurveyStudents(@Param("viewerId") Long viewerId);
    
    @Query(value = com.vn.DATN.Common.QueryCommon.GET_COMPLETED_SURVEY_STUDENTS, nativeQuery = true)
    Page<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithPagination(@Param("viewerId") Long viewerId, Pageable pageable);
    
    @Query(value = com.vn.DATN.Common.QueryCommon.GET_COMPLETED_SURVEY_STUDENTS_WITH_FILTER, nativeQuery = true)
    List<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithFilter(
        @Param("viewerId") Long viewerId,
        @Param("userId") String userId,
        @Param("userName") String userName,
        @Param("facultyName") String facultyName,
        @Param("className") String className,
        @Param("surveyTitle") String surveyTitle,
        @Param("submitTimeFrom") String submitTimeFrom,
        @Param("submitTimeTo") String submitTimeTo
    );
    
    @Query(value = com.vn.DATN.Common.QueryCommon.GET_COMPLETED_SURVEY_STUDENTS_WITH_FILTER, nativeQuery = true)
    Page<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithFilterAndPagination(
        @Param("viewerId") Long viewerId,
        @Param("userId") String userId,
        @Param("userName") String userName,
        @Param("facultyName") String facultyName,
        @Param("className") String className,
        @Param("surveyTitle") String surveyTitle,
        @Param("submitTimeFrom") String submitTimeFrom,
        @Param("submitTimeTo") String submitTimeTo,
        Pageable pageable
    );
} 