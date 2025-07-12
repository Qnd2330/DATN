package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.CompletedSurveyFilterDTO;
import com.vn.DATN.DTO.response.CompletedSurveyStudentResponse;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.Service.CompletedSurveyStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/completed-survey-students")
public class CompletedSurveyStudentController {
    private final CompletedSurveyStudentService completedSurveyStudentService;

    @GetMapping("/{viewerId}")
    public ResponseEntity<List<CompletedSurveyStudentResponse>> getCompletedSurveyStudents(
            @PathVariable Long viewerId) {
        try {
            List<CompletedSurveyStudentResponse> result = completedSurveyStudentService.getCompletedSurveyStudents(viewerId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{viewerId}/paginated")
    public ResponseEntity<PaginatedResponse<CompletedSurveyStudentResponse>> getCompletedSurveyStudentsWithPagination(
            @PathVariable Long viewerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CompletedSurveyStudentResponse> resultPage = completedSurveyStudentService.getCompletedSurveyStudentsWithPagination(viewerId, pageable);

            PaginatedResponse<CompletedSurveyStudentResponse> response = new PaginatedResponse<>(
                    resultPage.getContent(),
                    resultPage.getNumber(),
                    resultPage.getTotalElements(),
                    resultPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy danh sách sinh viên đã làm báo cáo theo ID người xem với filter
     * @param viewerId ID của người xem
     * @param userId Mã sinh viên (optional)
     * @param userName Tên sinh viên (optional)
     * @param facultyName Tên khoa (optional)
     * @param className Tên lớp (optional)
     * @param surveyTitle Tên báo cáo (optional)
     * @param submitTimeFrom Thời gian nộp từ (optional)
     * @param submitTimeTo Thời gian nộp đến (optional)
     * @return Danh sách sinh viên đã làm báo cáo có filter
     */
    @GetMapping("/{viewerId}/filter")
    public ResponseEntity<List<CompletedSurveyStudentResponse>> getCompletedSurveyStudentsWithFilter(
            @PathVariable Long viewerId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String facultyName,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String surveyTitle,
            @RequestParam(required = false) String submitTimeFrom,
            @RequestParam(required = false) String submitTimeTo) {
        try {
            // Xử lý userId - nếu rỗng thì set thành null
            if (userId != null && userId.trim().isEmpty()) {
                userId = null;
            }
            
            CompletedSurveyFilterDTO filter = new CompletedSurveyFilterDTO();
            filter.setUserId(userId);
            filter.setUserName(userName);
            filter.setFacultyName(facultyName);
            filter.setClassName(className);
            filter.setSurveyTitle(surveyTitle);
            filter.setSubmitTimeFrom(submitTimeFrom);
            filter.setSubmitTimeTo(submitTimeTo);
            
            List<CompletedSurveyStudentResponse> result = completedSurveyStudentService.getCompletedSurveyStudentsWithFilter(viewerId, filter);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Lấy danh sách sinh viên đã làm báo cáo theo ID người xem với filter và phân trang
     * @param viewerId ID của người xem
     * @param page Số trang (bắt đầu từ 0)
     * @param size Kích thước trang
     * @param userId Mã sinh viên (optional)
     * @param userName Tên sinh viên (optional)
     * @param facultyName Tên khoa (optional)
     * @param className Tên lớp (optional)
     * @param surveyTitle Tên báo cáo (optional)
     * @param submitTimeFrom Thời gian nộp từ (optional)
     * @param submitTimeTo Thời gian nộp đến (optional)
     * @return Danh sách sinh viên đã làm báo cáo có filter và phân trang
     */
    @GetMapping("/{viewerId}/filter/paginated")
    public ResponseEntity<PaginatedResponse<CompletedSurveyStudentResponse>> getCompletedSurveyStudentsWithFilterAndPagination(
            @PathVariable Long viewerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String facultyName,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String surveyTitle,
            @RequestParam(required = false) String submitTimeFrom,
            @RequestParam(required = false) String submitTimeTo) {
        try {
            // Xử lý userId - nếu rỗng thì set thành null
            if (userId != null && userId.trim().isEmpty()) {
                userId = null;
            }
            
            CompletedSurveyFilterDTO filter = new CompletedSurveyFilterDTO();
            filter.setUserId(userId);
            filter.setUserName(userName);
            filter.setFacultyName(facultyName);
            filter.setClassName(className);
            filter.setSurveyTitle(surveyTitle);
            filter.setSubmitTimeFrom(submitTimeFrom);
            filter.setSubmitTimeTo(submitTimeTo);
            
            Pageable pageable = PageRequest.of(page, size);
            Page<CompletedSurveyStudentResponse> resultPage = completedSurveyStudentService.getCompletedSurveyStudentsWithFilterAndPagination(viewerId, filter, pageable);

            PaginatedResponse<CompletedSurveyStudentResponse> response = new PaginatedResponse<>(
                    resultPage.getContent(),
                    resultPage.getNumber(),
                    resultPage.getTotalElements(),
                    resultPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 