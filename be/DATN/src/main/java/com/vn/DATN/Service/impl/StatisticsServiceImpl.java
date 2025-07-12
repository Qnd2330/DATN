package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.response.ClassStatisticsDTO;
import com.vn.DATN.DTO.response.ClassStatisticsProjection;
import com.vn.DATN.DTO.response.FacultyStatisticsDTO;
import com.vn.DATN.DTO.response.FacultyStatisticsProjection;
import com.vn.DATN.DTO.response.StatisticsOverviewDTO;
import com.vn.DATN.DTO.response.StatisticsOverviewProjection;
import com.vn.DATN.Service.StatisticsService;
import com.vn.DATN.Service.repositories.StatisticsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
    
    private final StatisticsRepo statisticsRepo;
    
    @Override
    public StatisticsOverviewDTO getStatisticsOverview(Long managerId) {
        try {
            log.info("Lấy thống kê tổng thể hệ thống cho manager ID: {}", managerId);
            StatisticsOverviewProjection result = statisticsRepo.getStatisticsOverview(managerId);
            if (result == null) {
                return new StatisticsOverviewDTO(0L, 0L, 0L, 0.0);
            }
            
            return new StatisticsOverviewDTO(
                result.getTotalSurveys(),
                result.getTotalStudents(),
                result.getCompletedSurveys(),
                result.getCompletionRate()
            );
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê tổng thể: {}", e.getMessage());
            throw new RuntimeException("Không thể lấy thống kê tổng thể: " + e.getMessage());
        }
    }
    
    @Override
    public List<FacultyStatisticsDTO> getStatisticsByFaculty(Long managerId) {
        try {
            log.info("Lấy thống kê theo khoa cho manager ID: {}", managerId);
            List<FacultyStatisticsProjection> results = statisticsRepo.getStatisticsByFaculty(managerId);
            if (results == null || results.isEmpty()) {
                return List.of();
            }
            
            return results.stream().map(projection -> 
                new FacultyStatisticsDTO(
                    projection.getFacultyName(),
                    projection.getTotalStudents(),
                    projection.getTotalSurveys(),
                    projection.getCompletedSurveys(),
                    projection.getCompletionRate()
                )
            ).toList();
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo khoa: {}", e.getMessage());
            throw new RuntimeException("Không thể lấy thống kê theo khoa: " + e.getMessage());
        }
    }
    
    @Override
    public List<ClassStatisticsDTO> getStatisticsByClass(Long managerId) {
        try {
            log.info("Lấy thống kê theo lớp cho manager ID: {}", managerId);
            List<ClassStatisticsProjection> results = statisticsRepo.getStatisticsByClass(managerId);
            if (results == null || results.isEmpty()) {
                return List.of();
            }
            
            return results.stream().map(projection -> 
                new ClassStatisticsDTO(
                    projection.getClassName(),
                    projection.getTotalStudents(),
                    projection.getTotalSurveys(),
                    projection.getCompletedSurveys(),
                    projection.getCompletionRate()
                )
            ).toList();
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo lớp: {}", e.getMessage());
            throw new RuntimeException("Không thể lấy thống kê theo lớp: " + e.getMessage());
        }
    }
    
    @Override
    public StatisticsOverviewDTO getStatisticsOverviewByDate(Long managerId, String startDate, String endDate) {
        try {
            log.info("Lấy thống kê tổng thể theo ngày từ {} đến {} cho manager ID: {}", startDate, endDate, managerId);
            StatisticsOverviewProjection result = statisticsRepo.getStatisticsOverviewByDate(managerId, startDate, endDate);
            if (result == null) {
                return new StatisticsOverviewDTO(0L, 0L, 0L, 0.0);
            }
            
            return new StatisticsOverviewDTO(
                result.getTotalSurveys(),
                result.getTotalStudents(),
                result.getCompletedSurveys(),
                result.getCompletionRate()
            );
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê tổng thể theo ngày: {}", e.getMessage());
            throw new RuntimeException("Không thể lấy thống kê tổng thể theo ngày: " + e.getMessage());
        }
    }
    
    @Override
    public List<FacultyStatisticsDTO> getStatisticsByFacultyByDate(Long managerId, String startDate, String endDate) {
        try {
            log.info("Lấy thống kê theo khoa theo ngày từ {} đến {} cho manager ID: {}", startDate, endDate, managerId);
            List<FacultyStatisticsProjection> results = statisticsRepo.getStatisticsByFacultyByDate(managerId, startDate, endDate);
            if (results == null || results.isEmpty()) {
                return List.of();
            }
            
            return results.stream().map(projection -> 
                new FacultyStatisticsDTO(
                    projection.getFacultyName(),
                    projection.getTotalStudents(),
                    projection.getTotalSurveys(),
                    projection.getCompletedSurveys(),
                    projection.getCompletionRate()
                )
            ).toList();
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo khoa theo ngày: {}", e.getMessage());
            throw new RuntimeException("Không thể lấy thống kê theo khoa theo ngày: " + e.getMessage());
        }
    }
    
    @Override
    public List<ClassStatisticsDTO> getStatisticsByClassByDate(Long managerId, String startDate, String endDate) {
        try {
            log.info("Lấy thống kê theo lớp theo ngày từ {} đến {} cho manager ID: {}", startDate, endDate, managerId);
            List<ClassStatisticsProjection> results = statisticsRepo.getStatisticsByClassByDate(managerId, startDate, endDate);
            if (results == null || results.isEmpty()) {
                return List.of();
            }
            
            return results.stream().map(projection -> 
                new ClassStatisticsDTO(
                    projection.getClassName(),
                    projection.getTotalStudents(),
                    projection.getTotalSurveys(),
                    projection.getCompletedSurveys(),
                    projection.getCompletionRate()
                )
            ).toList();
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo lớp theo ngày: {}", e.getMessage());
            throw new RuntimeException("Không thể lấy thống kê theo lớp theo ngày: " + e.getMessage());
        }
    }
} 