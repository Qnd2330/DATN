package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.CompletedSurveyFilterDTO;
import com.vn.DATN.DTO.response.CompletedSurveyStudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompletedSurveyStudentService {
    List<CompletedSurveyStudentResponse> getCompletedSurveyStudents(Long viewerId);
    Page<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithPagination(Long viewerId, Pageable pageable);
    
    /**
     * Lấy danh sách sinh viên đã làm báo cáo với filter
     * @param viewerId ID của người xem
     * @param filter DTO chứa các điều kiện filter
     * @return Danh sách sinh viên đã làm báo cáo có filter
     */
    List<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithFilter(Long viewerId, CompletedSurveyFilterDTO filter);
    
    /**
     * Lấy danh sách sinh viên đã làm báo cáo với filter và phân trang
     * @param viewerId ID của người xem
     * @param filter DTO chứa các điều kiện filter
     * @param pageable Thông tin phân trang
     * @return Danh sách sinh viên đã làm báo cáo có filter và phân trang
     */
    Page<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithFilterAndPagination(Long viewerId, CompletedSurveyFilterDTO filter, Pageable pageable);
} 