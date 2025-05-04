package com.vn.DATN.Controller;

import com.vn.DATN.DTO.mapper.QuestionMapper;
import com.vn.DATN.DTO.mapper.SurveyMapper;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.Service.SurveyAndQuestionService;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody SurveyDTO surveyDTO) {
        try {
            surveyDTO.setSurveyId(id);
            Survey updated = surveyService.edit(surveyDTO);
            SurveyDTO result = SurveyMapper.INSTANCE.toDTO(updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            boolean deleted = surveyAndQuestionService.delete(id);
            if (deleted) {
                return ResponseEntity.ok("Xóa thành công");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Xóa thất bại do một phần dữ liệu không thể xóa");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi: " + e.getMessage());
        }
    }
}
