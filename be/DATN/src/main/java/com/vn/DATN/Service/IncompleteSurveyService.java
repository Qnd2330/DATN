package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.IncompleteSurveyFilterDTO;
import com.vn.DATN.DTO.response.IncompleteSurveyResponse;

import java.util.List;

public interface IncompleteSurveyService {
    /**
     * Lấy danh sách sinh viên chưa hoàn thành đánh giá với phân trang
     * @param viewerId ID của người xem
     * @param page Số trang (bắt đầu từ 0)
     * @param size Kích thước trang
     * @return Danh sách sinh viên chưa hoàn thành đánh giá có phân trang
     */
    List<IncompleteSurveyResponse> getIncompleteSurveysByViewerIdWithPagination(Long viewerId, int page, int size);
    
    /**
     * Đếm tổng số sinh viên chưa hoàn thành đánh giá
     * @param viewerId ID của người xem
     * @return Tổng số sinh viên chưa hoàn thành
     */
    long countIncompleteSurveysByViewerId(Long viewerId);
    
    /**
     * Lấy danh sách sinh viên chưa hoàn thành đánh giá với filter
     * @param viewerId ID của người xem
     * @param filter DTO chứa các điều kiện filter
     * @return Danh sách sinh viên chưa hoàn thành đánh giá có filter
     */
    List<IncompleteSurveyResponse> getIncompleteSurveysByViewerIdWithFilter(Long viewerId, IncompleteSurveyFilterDTO filter);
} 