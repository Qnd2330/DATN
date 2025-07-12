package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.response.IncompleteSurveyProjection;
import com.vn.DATN.entity.DummyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncompleteSurveyRepo extends JpaRepository<DummyEntity, Long> {
    
    @Query(value = QueryCommon.GET_INCOMPLETE_SURVEYS_BY_VIEWER_ID, nativeQuery = true)
    List<IncompleteSurveyProjection> findIncompleteSurveysByViewerId(@Param("viewerId") Long viewerId);
    
    @Query(value = QueryCommon.GET_INCOMPLETE_SURVEYS_BY_VIEWER_ID_WITH_FILTER, nativeQuery = true)
    List<IncompleteSurveyProjection> findIncompleteSurveysByViewerIdWithFilter(
        @Param("viewerId") Long viewerId,
        @Param("userId") String userId,
        @Param("userName") String userName,
        @Param("facultyName") String facultyName,
        @Param("className") String className,
        @Param("surveyName") String surveyName,
        @Param("deadlineFrom") String deadlineFrom,
        @Param("deadlineTo") String deadlineTo
    );
} 