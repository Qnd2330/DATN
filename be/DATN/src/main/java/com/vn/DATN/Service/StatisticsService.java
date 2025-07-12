package com.vn.DATN.Service;

import com.vn.DATN.DTO.response.ClassStatisticsDTO;
import com.vn.DATN.DTO.response.FacultyStatisticsDTO;
import com.vn.DATN.DTO.response.StatisticsOverviewDTO;

import java.util.List;

public interface StatisticsService {
    
    /**
     * Lấy thống kê tổng thể hệ thống (chỉ cho MANAGER)
     * @param managerId ID của manager
     * @return StatisticsOverviewDTO chứa thông tin tổng quan
     */
    StatisticsOverviewDTO getStatisticsOverview(Long managerId);
    
    /**
     * Lấy thống kê theo khoa (chỉ cho MANAGER)
     * @param managerId ID của manager
     * @return List<FacultyStatisticsDTO> danh sách thống kê theo khoa
     */
    List<FacultyStatisticsDTO> getStatisticsByFaculty(Long managerId);
    
    /**
     * Lấy thống kê theo lớp (chỉ cho MANAGER)
     * @param managerId ID của manager
     * @return List<ClassStatisticsDTO> danh sách thống kê theo lớp
     */
    List<ClassStatisticsDTO> getStatisticsByClass(Long managerId);
    
    /**
     * Lấy thống kê tổng thể theo ngày tháng (chỉ cho MANAGER)
     * @param managerId ID của manager
     * @param startDate Ngày bắt đầu (yyyy-MM-dd)
     * @param endDate Ngày kết thúc (yyyy-MM-dd)
     * @return StatisticsOverviewDTO thống kê tổng thể theo ngày
     */
    StatisticsOverviewDTO getStatisticsOverviewByDate(Long managerId, String startDate, String endDate);
    
    /**
     * Lấy thống kê theo khoa theo ngày tháng (chỉ cho MANAGER)
     * @param managerId ID của manager
     * @param startDate Ngày bắt đầu (yyyy-MM-dd)
     * @param endDate Ngày kết thúc (yyyy-MM-dd)
     * @return List<FacultyStatisticsDTO> danh sách thống kê theo khoa theo ngày
     */
    List<FacultyStatisticsDTO> getStatisticsByFacultyByDate(Long managerId, String startDate, String endDate);
    
    /**
     * Lấy thống kê theo lớp theo ngày tháng (chỉ cho MANAGER)
     * @param managerId ID của manager
     * @param startDate Ngày bắt đầu (yyyy-MM-dd)
     * @param endDate Ngày kết thúc (yyyy-MM-dd)
     * @return List<ClassStatisticsDTO> danh sách thống kê theo lớp theo ngày
     */
    List<ClassStatisticsDTO> getStatisticsByClassByDate(Long managerId, String startDate, String endDate);
} 