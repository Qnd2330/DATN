package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.Service.SurveyResultService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/survey")
@RequiredArgsConstructor
public class SurveyResultController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final SurveyResultService surveyResultService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS') or hasAuthority('GET_ACCESS')")
    public ResponseEntity<?> listUnfinishedPersonalReports(@PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(surveyResultService.listUnfinishedPersonalReports(userId,pageable));
        } catch (RuntimeException ex) {
            log.error("Lỗi: ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/monitor/{userId}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS') or hasAuthority('GET_ACCESS')")
    public ResponseEntity<?> listSurveyResultOfMonitor(@PathVariable Integer userId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(surveyResultService.listSurveyResultOfMonitor(userId,pageable));
        } catch (RuntimeException ex) {
            log.error("Lỗi: ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/teacher/{userId}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS') or hasAuthority('GET_ACCESS')")
    public ResponseEntity<?> listSurveyResultOfTeacher(@PathVariable Integer userId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(surveyResultService.listSurveyResultOfTeacher(userId,pageable));
        } catch (RuntimeException ex) {
            log.error("Lỗi: ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS')")
    public ResponseEntity<?> getSurveyResultDetailNested(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(surveyResultService.getSurveyResultDetailNested(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("done/{userId}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS') or hasAuthority('GET_ACCESS')")
    public ResponseEntity<?> listFinishedPersonalReports(@PathVariable Integer userId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(surveyResultService.findRatedSurveyResultsForStudent(userId,pageable));
        } catch (RuntimeException ex) {
            log.error("Lỗi: ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("complete/{userId}")
    @PreAuthorize("hasAuthority('GET_SURVEY_ACCESS') or hasAuthority('GET_ACCESS')")
    public ResponseEntity<?> listFinishedReports(@PathVariable Integer userId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(surveyResultService.findRatedSurveyResultsForMonitorAndStudent(userId,pageable));
        } catch (RuntimeException ex) {
            log.error("Lỗi: ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('SUBMIT_SURVEY_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> submit (@RequestBody SubmissionDTO submissionDTO){
        try {
            surveyResultService.submitSurvey(submissionDTO);
            return ResponseEntity.ok("Đánh giá thành công!");
        } catch (RuntimeException ex) {
            log.error("Lỗi khi nộp báo cáo", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
