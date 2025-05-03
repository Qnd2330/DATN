package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.Service.SurveyAndQuestionService;
import com.vn.DATN.Service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;
    private final SurveyAndQuestionService surveyAndQuestionService;

    @PostMapping("/creat")
    public ResponseEntity<?> creat (@RequestBody SurveyDTO surveyDTO){
        try {
            SurveyDTO created = surveyAndQuestionService.create(surveyDTO);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
