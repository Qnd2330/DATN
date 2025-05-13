package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.Service.SurveyResultService;
import com.vn.DATN.entity.SurveyResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class SurveyResultController {

    private final SurveyResultService surveyResultService;

    @PostMapping("/submit")
    public ResponseEntity<?> submit (@RequestBody SubmissionDTO submissionDTO){
        try {
            SurveyResult created = surveyResultService.submitSurvey(submissionDTO);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
