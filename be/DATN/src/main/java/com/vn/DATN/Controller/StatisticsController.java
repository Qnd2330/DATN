package com.vn.DATN.Controller;

import com.vn.DATN.DTO.response.ClassStatisticsDTO;
import com.vn.DATN.DTO.response.FacultyStatisticsDTO;
import com.vn.DATN.DTO.response.StatisticsOverviewDTO;
import com.vn.DATN.Service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    
    private final StatisticsService statisticsService;
    
    /**
     * Lấy thống kê tổng thể hệ thống (chỉ cho MANAGER)
     * @return ResponseEntity<StatisticsOverviewDTO>
     */
    @GetMapping("/overview")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<StatisticsOverviewDTO> getStatisticsOverview() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy thống kê tổng thể hệ thống cho manager ID: {}", managerId);
            StatisticsOverviewDTO result = statisticsService.getStatisticsOverview(managerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê tổng thể: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatisticsOverviewDTO(0L, 0L, 0L, 0.0));
        }
    }
    
    /**
     * Lấy thống kê theo khoa (chỉ cho MANAGER)
     * @return ResponseEntity<List<FacultyStatisticsDTO>>
     */
    @GetMapping("/by-faculty")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<List<FacultyStatisticsDTO>> getStatisticsByFaculty() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy thống kê theo khoa cho manager ID: {}", managerId);
            List<FacultyStatisticsDTO> result = statisticsService.getStatisticsByFaculty(managerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo khoa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    /**
     * Lấy thống kê theo lớp (chỉ cho MANAGER)
     * @return ResponseEntity<List<ClassStatisticsDTO>>
     */
    @GetMapping("/by-class")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<List<ClassStatisticsDTO>> getStatisticsByClass() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy thống kê theo lớp cho manager ID: {}", managerId);
            List<ClassStatisticsDTO> result = statisticsService.getStatisticsByClass(managerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo lớp: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    /**
     * Lấy thống kê tổng thể theo ngày tháng (chỉ cho MANAGER)
     * @param startDate Ngày bắt đầu (yyyy-MM-dd)
     * @param endDate Ngày kết thúc (yyyy-MM-dd)
     * @return ResponseEntity<StatisticsOverviewDTO>
     */
    @GetMapping("/overview/by-date")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<StatisticsOverviewDTO> getStatisticsOverviewByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy thống kê tổng thể theo ngày từ {} đến {} cho manager ID: {}", startDate, endDate, managerId);
            StatisticsOverviewDTO result = statisticsService.getStatisticsOverviewByDate(managerId, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê tổng thể theo ngày: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatisticsOverviewDTO(0L, 0L, 0L, 0.0));
        }
    }
    
    /**
     * Lấy thống kê theo khoa theo ngày tháng (chỉ cho MANAGER)
     * @param startDate Ngày bắt đầu (yyyy-MM-dd)
     * @param endDate Ngày kết thúc (yyyy-MM-dd)
     * @return ResponseEntity<List<FacultyStatisticsDTO>>
     */
    @GetMapping("/by-faculty/by-date")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<List<FacultyStatisticsDTO>> getStatisticsByFacultyByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy thống kê theo khoa theo ngày từ {} đến {} cho manager ID: {}", startDate, endDate, managerId);
            List<FacultyStatisticsDTO> result = statisticsService.getStatisticsByFacultyByDate(managerId, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo khoa theo ngày: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    /**
     * Lấy thống kê theo lớp theo ngày tháng (chỉ cho MANAGER)
     * @param startDate Ngày bắt đầu (yyyy-MM-dd)
     * @param endDate Ngày kết thúc (yyyy-MM-dd)
     * @return ResponseEntity<List<ClassStatisticsDTO>>
     */
    @GetMapping("/by-class/by-date")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<List<ClassStatisticsDTO>> getStatisticsByClassByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy thống kê theo lớp theo ngày từ {} đến {} cho manager ID: {}", startDate, endDate, managerId);
            List<ClassStatisticsDTO> result = statisticsService.getStatisticsByClassByDate(managerId, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Lỗi khi lấy thống kê theo lớp theo ngày: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
    
    /**
     * Lấy tất cả thống kê (chỉ cho MANAGER)
     * @param dateFrom Ngày bắt đầu (yyyy-MM-dd) - optional
     * @param dateTo Ngày kết thúc (yyyy-MM-dd) - optional
     * @return ResponseEntity với tất cả dữ liệu thống kê
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<AllStatisticsResponse> getAllStatistics(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long managerId = Long.parseLong(authentication.getName());
            
            log.info("API: Lấy tất cả thống kê cho manager ID: {} với filter ngày từ {} đến {}", 
                    managerId, dateFrom, dateTo);
            
            StatisticsOverviewDTO overview;
            List<FacultyStatisticsDTO> facultyStats;
            List<ClassStatisticsDTO> classStats;
            
            // Nếu có filter ngày thì sử dụng API có filter
            if (dateFrom != null || dateTo != null) {
                String startDate = dateFrom != null ? dateFrom : "1900-01-01";
                String endDate = dateTo != null ? dateTo : "2100-12-31";
                
                overview = statisticsService.getStatisticsOverviewByDate(managerId, startDate, endDate);
                facultyStats = statisticsService.getStatisticsByFacultyByDate(managerId, startDate, endDate);
                classStats = statisticsService.getStatisticsByClassByDate(managerId, startDate, endDate);
            } else {
                // Nếu không có filter ngày thì sử dụng API thông thường
                overview = statisticsService.getStatisticsOverview(managerId);
                facultyStats = statisticsService.getStatisticsByFaculty(managerId);
                classStats = statisticsService.getStatisticsByClass(managerId);
            }
            
            AllStatisticsResponse response = new AllStatisticsResponse(overview, facultyStats, classStats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Lỗi khi lấy tất cả thống kê: {}", e.getMessage());
            AllStatisticsResponse errorResponse = new AllStatisticsResponse(
                new StatisticsOverviewDTO(0L, 0L, 0L, 0.0), 
                List.of(), 
                List.of()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    // Inner class để tránh lỗi linter
    public static class AllStatisticsResponse {
        private final StatisticsOverviewDTO overview;
        private final List<FacultyStatisticsDTO> facultyStatistics;
        private final List<ClassStatisticsDTO> classStatistics;
        
        public AllStatisticsResponse(StatisticsOverviewDTO overview, 
                                   List<FacultyStatisticsDTO> facultyStatistics, 
                                   List<ClassStatisticsDTO> classStatistics) {
            this.overview = overview;
            this.facultyStatistics = facultyStatistics;
            this.classStatistics = classStatistics;
        }
        
        public StatisticsOverviewDTO getOverview() { return overview; }
        public List<FacultyStatisticsDTO> getFacultyStatistics() { return facultyStatistics; }
        public List<ClassStatisticsDTO> getClassStatistics() { return classStatistics; }
    }
} 