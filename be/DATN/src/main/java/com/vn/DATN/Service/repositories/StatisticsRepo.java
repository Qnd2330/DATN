package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.response.ClassStatisticsProjection;
import com.vn.DATN.DTO.response.FacultyStatisticsProjection;
import com.vn.DATN.DTO.response.StatisticsOverviewProjection;
import com.vn.DATN.entity.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepo extends JpaRepository<DummyEntity, Long> {
    
    @Query(value = QueryCommon.GET_STATISTICS_OVERVIEW, nativeQuery = true)
    StatisticsOverviewProjection getStatisticsOverview(@Param("managerId") Long managerId);
    
    @Query(value = QueryCommon.GET_STATISTICS_BY_FACULTY, nativeQuery = true)
    List<FacultyStatisticsProjection> getStatisticsByFaculty(@Param("managerId") Long managerId);
    
    @Query(value = QueryCommon.GET_STATISTICS_BY_CLASS, nativeQuery = true)
    List<ClassStatisticsProjection> getStatisticsByClass(@Param("managerId") Long managerId);
    
    @Query(value = QueryCommon.GET_STATISTICS_OVERVIEW_BY_DATE, nativeQuery = true)
    StatisticsOverviewProjection getStatisticsOverviewByDate(@Param("managerId") Long managerId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Query(value = QueryCommon.GET_STATISTICS_BY_FACULTY_BY_DATE, nativeQuery = true)
    List<FacultyStatisticsProjection> getStatisticsByFacultyByDate(@Param("managerId") Long managerId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Query(value = QueryCommon.GET_STATISTICS_BY_CLASS_BY_DATE, nativeQuery = true)
    List<ClassStatisticsProjection> getStatisticsByClassByDate(@Param("managerId") Long managerId, @Param("startDate") String startDate, @Param("endDate") String endDate);
} 