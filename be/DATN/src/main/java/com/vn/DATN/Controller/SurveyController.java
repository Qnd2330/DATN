package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.DTO.response.SurveyResponse;
import com.vn.DATN.Service.CourseAndSurveyService;
import com.vn.DATN.Service.SurveyAndQuestionService;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.entity.CourseSurvey;
import com.vn.DATN.entity.Survey;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final SurveyService surveyService;
    private final SurveyAndQuestionService surveyAndQuestionService;

    private final CourseAndSurveyService courseAndSurveyService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Survey> surveyPage = surveyService.getAll(pageable);

            List<SurveyResponse> userResponse = surveyPage
                    .stream()
                    .map(survey -> {
                        SurveyResponse.SurveyResponseBuilder builder = SurveyResponse.builder()
                                .surveyId(survey.getSurveyId())
                                .title(survey.getTitle())
                                .description(survey.getDescription());

                        Optional<CourseSurvey> courseSurveyOpt = courseAndSurveyService.findBySurvey(survey);
                        courseSurveyOpt.ifPresent(courseSurvey -> builder.courseName(courseSurvey.getCourse().getCourseName()));

                        return builder.build();
                    })
                    .collect(Collectors.toList());

            PaginatedResponse<SurveyResponse> response = new PaginatedResponse<>(
                    userResponse,
                    surveyPage.getNumber(),
                    surveyPage.getTotalElements(),
                    surveyPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách bản báo cáo", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        try {
            SurveyDTO response = surveyService.getById(id);
            return ResponseEntity.ok(response);
        }catch (RuntimeException ex) {
            log.error("Lỗi khi lấy bản báo cáo", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_SURVEY_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create (@RequestBody SurveyDTO surveyDTO){
        try {
            SurveyDTO created = surveyAndQuestionService.create(surveyDTO);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi tạo bản báo cáo", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_SURVEY_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody SurveyDTO surveyDTO) {
        try {
            surveyDTO.setSurveyId(id);
            SurveyDTO update = surveyAndQuestionService.update(surveyDTO);
            return ResponseEntity.ok(update);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi cập nhật bản báo cáo", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_SURVEY_ACCESS') or hasAuthority('DELETE_ACCESS')")
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
