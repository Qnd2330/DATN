package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.IncompleteSurveyFilterDTO;
import com.vn.DATN.DTO.response.IncompleteSurveyProjection;
import com.vn.DATN.DTO.response.IncompleteSurveyResponse;
import com.vn.DATN.Service.IncompleteSurveyService;
import com.vn.DATN.Service.repositories.IncompleteSurveyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncompleteSurveyServiceImpl implements IncompleteSurveyService {

    private final IncompleteSurveyRepo incompleteSurveyRepo;

    @Override
    public List<IncompleteSurveyResponse> getIncompleteSurveysByViewerIdWithPagination(Long viewerId, int page, int size) {
        try {
            // Lấy tất cả dữ liệu trước
            List<IncompleteSurveyProjection> allProjections = incompleteSurveyRepo.findIncompleteSurveysByViewerId(viewerId);

            // Thực hiện phân trang thủ công
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, allProjections.size());

            if (startIndex >= allProjections.size()) {
                return List.of();
            }

            List<IncompleteSurveyProjection> paginatedProjections = allProjections.subList(startIndex, endIndex);

            return paginatedProjections.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách sinh viên chưa hoàn thành đánh giá với phân trang: " + e.getMessage(), e);
        }
    }

    @Override
    public long countIncompleteSurveysByViewerId(Long viewerId) {
        try {
            List<IncompleteSurveyProjection> projections = incompleteSurveyRepo.findIncompleteSurveysByViewerId(viewerId);
            return projections.size();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đếm số sinh viên chưa hoàn thành đánh giá: " + e.getMessage(), e);
        }
    }

    @Override
    public List<IncompleteSurveyResponse> getIncompleteSurveysByViewerIdWithFilter(Long viewerId, IncompleteSurveyFilterDTO filter) {
        try {
            // Xử lý userId - nếu rỗng hoặc null thì set thành null
            String userId = filter.getUserId();
            if (userId != null && userId.trim().isEmpty()) {
                userId = null;
            }
            
            List<IncompleteSurveyProjection> projections = incompleteSurveyRepo.findIncompleteSurveysByViewerIdWithFilter(
                    viewerId,
                    userId,
                    filter.getUserName(),
                    filter.getFacultyName(),
                    filter.getClassName(),
                    filter.getSurveyName(),
                    filter.getDeadlineFrom(),
                    filter.getDeadlineTo()
            );

            return projections.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách sinh viên chưa hoàn thành đánh giá với filter: " + e.getMessage(), e);
        }
    }

    /**
     * Map từ projection sang response DTO
     */
    private IncompleteSurveyResponse mapToResponse(IncompleteSurveyProjection projection) {
        return new IncompleteSurveyResponse(
                projection.getUserId(),
                projection.getUserName(),
                projection.getSurveyName(),
                projection.getFacultyName(),
                projection.getClassName(),
                projection.getDeadline()
        );
    }
} 