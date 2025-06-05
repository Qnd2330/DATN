package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.SurveyDTO;
import com.vn.DATN.DTO.response.SurveyQuestionAnswerResponse;
import com.vn.DATN.Service.CourseAndSurveyService;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.Service.SurveyService;
import com.vn.DATN.Service.repositories.SurveyRepo;
import com.vn.DATN.entity.Course;
import com.vn.DATN.entity.Survey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepo surveyRepo;

    private final CourseService courseService;

    private final CourseAndSurveyService courseAndSurveyService;

    @Override
    public Page<Survey> getAll(Pageable pageable) {
        return surveyRepo.findAll(pageable);
    }

    @Override
    @Transactional
    public Survey create(SurveyDTO request) {
        Survey survey = Survey.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        surveyRepo.save(survey);

        linkCourseIfNecessary(request, survey);

        return survey;
    }

    @Override
    @Transactional
    public Survey edit(SurveyDTO request) {
        Survey survey = surveyRepo.findById(request.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey không tồn tại"));

        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        surveyRepo.saveAndFlush(survey);

        linkCourseIfNecessary(request, survey);

        return survey;
    }

    @Override
    public Survey findById(Integer surveyId) {
        return surveyRepo.findById(surveyId).orElse(null);
    }

    @Override
    public SurveyDTO getById(Integer surveyId) {
        List<SurveyQuestionAnswerResponse> flatList = surveyRepo.getSurveyDetail(surveyId);

        if (flatList.isEmpty()) {
            throw new RuntimeException("Survey not found with id: " + surveyId);
        }

        SurveyDTO survey = new SurveyDTO();
        Map<Integer, QuestionDTO> questionMap = new LinkedHashMap<>();

        for (SurveyQuestionAnswerResponse row : flatList) {
            // Khởi tạo dữ liệu survey
            if (survey.getSurveyId() == null) {
                survey.setSurveyId(row.getSurveyId());
                survey.setTitle(row.getSurveyTitle());
                survey.setDescription(row.getDescription());
                survey.setCourseName(row.getCourseName());
            }

            // Group theo câu hỏi
            QuestionDTO question = questionMap.computeIfAbsent(row.getQuestionId(), qId -> {
                QuestionDTO q = new QuestionDTO();
                q.setQuestionId(qId);
                q.setQuestionText(row.getQuestionText());
                q.setType(row.getQuestionType());
                return q;
            });

            // Thêm câu trả lời vào câu hỏi
            question.getAnswers().add(new AnswerDTO(
                    row.getAnswerId(),
                    row.getAnswerContent(),
                    row.getAnswerPoint()
            ));
        }

        survey.setQuestionDTO(new ArrayList<>(questionMap.values()));
        return survey;
    }

    @Override
    public boolean existsById(Integer surveyId) {
        if (surveyId == null) {
            return false;
        }
        return surveyRepo.existsById(surveyId);
    }

    @Override
    public boolean delete(Integer surveyId) {
        if (!surveyRepo.existsById(surveyId)) {
            return false;
        }
        surveyRepo.deleteById(surveyId);
        return true;
    }

    private void linkCourseIfNecessary(SurveyDTO request, Survey survey) {
        if (request.isHaveCourse()) {
            if (request.getCourseName() == null || request.getCourseName().isBlank()) {
                throw new RuntimeException("Tên môn học không được để trống");
            }

            Course course = courseService.findByCourseName(request.getCourseName());
            if (course == null) {
                throw new RuntimeException("Không tìm thấy môn học");
            }

            courseAndSurveyService.linkSurveyToCourse(course, survey);
        }
    }
}
