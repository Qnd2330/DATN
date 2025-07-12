package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.IncompleteSurveyFilterDTO;
import com.vn.DATN.DTO.response.IncompleteSurveyResponse;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.Service.IncompleteSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomplete-surveys")
@RequiredArgsConstructor
public class IncompleteSurveyController {

    private final IncompleteSurveyService incompleteSurveyService;

    /**
     * Lấy danh sách sinh viên chưa hoàn thành đánh giá theo ID người xem với phân trang
     * @param viewerId ID của người xem
     * @param page Số trang (bắt đầu từ 0)
     * @param size Kích thước trang
     * @return Danh sách sinh viên chưa hoàn thành đánh giá có phân trang
     */
    @GetMapping("/list/{viewerId}/paginated")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<PaginatedResponse<IncompleteSurveyResponse>> getIncompleteSurveysByViewerIdWithPagination(
            @PathVariable Long viewerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<IncompleteSurveyResponse> incompleteSurveys = incompleteSurveyService.getIncompleteSurveysByViewerIdWithPagination(viewerId, page, size);
            long totalCount = incompleteSurveyService.countIncompleteSurveysByViewerId(viewerId);
            
            PaginatedResponse<IncompleteSurveyResponse> response = new PaginatedResponse<>();
            response.setContent(incompleteSurveys);
            response.setTotalItems(totalCount);
            response.setTotalPages((int) Math.ceil((double) totalCount / size));
            response.setCurrentPage(page);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Lấy danh sách sinh viên chưa hoàn thành đánh giá theo ID người xem với filter
     * @param viewerId ID của người xem
     * @param userId Mã sinh viên (optional)
     * @param userName Tên sinh viên (optional)
     * @param facultyName Tên khoa (optional)
     * @param className Tên lớp (optional)
     * @param surveyName Tên khảo sát (optional)
     * @param deadlineFrom Hạn nộp từ (optional)
     * @param deadlineTo Hạn nộp đến (optional)
     * @return Danh sách sinh viên chưa hoàn thành đánh giá có filter
     */
    @GetMapping("/list/{viewerId}/filter")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<List<IncompleteSurveyResponse>> getIncompleteSurveysByViewerIdWithFilter(
            @PathVariable Long viewerId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String facultyName,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String surveyName,
            @RequestParam(required = false) String deadlineFrom,
            @RequestParam(required = false) String deadlineTo) {
        try {
            // Xử lý userId - nếu rỗng thì set thành null
            if (userId != null && userId.trim().isEmpty()) {
                userId = null;
            }
            
            IncompleteSurveyFilterDTO filter = new IncompleteSurveyFilterDTO();
            filter.setUserId(userId);
            filter.setUserName(userName);
            filter.setFacultyName(facultyName);
            filter.setClassName(className);
            filter.setSurveyName(surveyName);
            filter.setDeadlineFrom(deadlineFrom);
            filter.setDeadlineTo(deadlineTo);
            
            List<IncompleteSurveyResponse> incompleteSurveys = incompleteSurveyService.getIncompleteSurveysByViewerIdWithFilter(viewerId, filter);
            
            return ResponseEntity.ok(incompleteSurveys);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
} 