package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.CompletedSurveyFilterDTO;
import com.vn.DATN.DTO.response.CompletedSurveyStudentResponse;
import com.vn.DATN.Service.CompletedSurveyStudentService;
import com.vn.DATN.Service.repositories.CompletedSurveyStudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedSurveyStudentServiceImpl implements CompletedSurveyStudentService {

    private final CompletedSurveyStudentRepo completedSurveyStudentRepo;

    @Override
    public List<CompletedSurveyStudentResponse> getCompletedSurveyStudents(Long viewerId) {
        return completedSurveyStudentRepo.getCompletedSurveyStudents(viewerId);
    }

    @Override
    public Page<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithPagination(Long viewerId, Pageable pageable) {
        return completedSurveyStudentRepo.getCompletedSurveyStudentsWithPagination(viewerId, pageable);
    }

    @Override
    public List<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithFilter(Long viewerId, CompletedSurveyFilterDTO filter) {
        // Xử lý userId - nếu rỗng hoặc null thì set thành null
        String userId = filter.getUserId();
        if (userId != null && userId.trim().isEmpty()) {
            userId = null;
        }
        
        return completedSurveyStudentRepo.getCompletedSurveyStudentsWithFilter(
                viewerId,
                userId,
                filter.getUserName(),
                filter.getFacultyName(),
                filter.getClassName(),
                filter.getSurveyTitle(),
                filter.getSubmitTimeFrom(),
                filter.getSubmitTimeTo()
        );
    }

    @Override
    public Page<CompletedSurveyStudentResponse> getCompletedSurveyStudentsWithFilterAndPagination(Long viewerId, CompletedSurveyFilterDTO filter, Pageable pageable) {
        // Xử lý userId - nếu rỗng hoặc null thì set thành null
        String userId = filter.getUserId();
        if (userId != null && userId.trim().isEmpty()) {
            userId = null;
        }
        
        return completedSurveyStudentRepo.getCompletedSurveyStudentsWithFilterAndPagination(
                viewerId,
                userId,
                filter.getUserName(),
                filter.getFacultyName(),
                filter.getClassName(),
                filter.getSurveyTitle(),
                filter.getSubmitTimeFrom(),
                filter.getSubmitTimeTo(),
                pageable
        );
    }
} 