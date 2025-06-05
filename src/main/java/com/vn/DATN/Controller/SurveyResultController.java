package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.DTO.response.SurveyCourseTeacherResponse;
import com.vn.DATN.Service.SurveyResultService;
import com.vn.DATN.entity.SurveyResult;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class SurveyResultController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final SurveyResultService surveyResultService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS') or hasAuthority('GET_ACCESS')")
    public Page<SurveyCourseTeacherResponse> getPagedSurveys(@PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SurveyCourseTeacherResponse> responses = surveyResultService.getSurveyWithPagination(userId,pageable);
        return responses;
    }

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('SUBMIT_SURVEY_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> submit (@RequestBody SubmissionDTO submissionDTO){
        try {
            SurveyResult created = surveyResultService.submitSurvey(submissionDTO);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi nộp báo cáo", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
