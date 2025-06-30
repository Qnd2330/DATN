package com.vn.DATN.Service.impl;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.Common.QuestionType;
import com.vn.DATN.DTO.request.*;
import com.vn.DATN.DTO.response.*;
import com.vn.DATN.Service.SurveyResultService;
import com.vn.DATN.Service.repositories.*;
import com.vn.DATN.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurveyResultServiceImpl implements SurveyResultService {
    private final SurveyResultRepo surveyResultRepo;
    private final QuestionAndAnswerRepo questionAndAnswerRepo;
    private final UserAndAnswersRepo userAndAnswersRepo;
    private final BasicBeanRemote basicBeanRemote;
    private static final Logger log = LoggerFactory.getLogger(SurveyResultServiceImpl.class);

    @Override
    public SurveyResult submitSurvey(SubmissionDTO submissionDTO) {
        SurveyResult surveyResult = new SurveyResult();

        Users user = loadUser(submissionDTO.getUserId());
        Users evaluator = loadEvaluator(submissionDTO.getEvaluatorId());
        Survey survey = loadSurvey(submissionDTO.getSurveyId());

        surveyResult.setUsers(user);
        surveyResult.setEvaluator(evaluator);
        surveyResult.setSurvey(survey);
        surveyResultRepo.save(surveyResult);

        for (UserAnswerDTO userAnswerDTO : submissionDTO.getUserAnswers()) {
            Question question = loadQuestion(userAnswerDTO.getQuestionId());
            Answer answer = resolveAnswer(question, userAnswerDTO);

            QuestionAnswer questionAnswer = questionAndAnswerRepo
                    .findByQuestionAndAnswer(question, answer)
                    .orElseGet(() -> questionAndAnswerRepo.save(
                            QuestionAnswer.builder()
                                    .question(question)
                                    .answer(answer)
                                    .build()
                    ));

            UserAnswer userAnswer = UserAnswer.builder()
                    .surveyResult(surveyResult)
                    .questionAnswer(questionAnswer)
                    .build();

            userAndAnswersRepo.save(userAnswer);
        }

        return surveyResult;
    }

    @Override
    public Page<SurveyCourseTeacherResponse> listUnfinishedPersonalReports(Integer userId, Pageable pageable) {
        try {
            return surveyResultRepo.getSurveySummaries(userId, pageable);
        } catch (Exception ex){
            System.out.println("Lỗi: " + ex.getMessage());
            throw new RuntimeException("Không thể lấy danh sách các bản báo cáo cá nhân");
        }
    }

    @Override
    public Page<SurveyResultResponse> listSurveyResultOfMonitor(Integer userId, Pageable pageable) {
        try {
            return surveyResultRepo.getSurveyResultOfMonitor(userId, pageable);
        } catch (Exception ex){
            System.out.println("Lỗi: " + ex.getMessage());
            throw new RuntimeException("Không thể lấy danh sách các bản báo cáo của sinh viên");
        }
    }

    public Page<SurveyResultResponse> listSurveyResultOfTeacher(Integer userId, Pageable pageable) {
        try {
            return surveyResultRepo.getSurveyResultOfTeacher(userId, pageable);
        } catch (Exception ex){
            System.out.println("Lỗi: " + ex.getMessage());
            throw new RuntimeException("Không thể lấy danh sách các bản báo cáo của sinh viên");
        }
    }

    @Override
    public SurveyResultDetailDTO getSurveyResultDetailNested(Integer surveyResultId) {
        List<SurveyResultDetailResponse> flatList = surveyResultRepo.getSurveyResultDetailNested(surveyResultId);

        if (flatList.isEmpty()) {
            throw new RuntimeException("Không tìm thấy dữ liệu");
        }

        SurveyResultDetailResponse first = flatList.get(0);

        SurveyResultDetailDTO dto = new SurveyResultDetailDTO();
        dto.setSurveyResultId(first.getSurveyResultId());

        dto.setUserId(first.getUserId());
        dto.setUserName(first.getUserName());
        dto.setEmail(first.getEmail());

        dto.setEvaluatorId(first.getEvaluatorId());
        dto.setEvaluatorName(first.getEvaluatorName());

        dto.setSurveyId(first.getSurveyId());
        dto.setTitle(first.getTitle());
        dto.setDescription(first.getDescription());
        dto.setDeadline(first.getDeadline());

        dto.setCourseId(first.getCourseId());
        dto.setCourseName(first.getCourseName());

        Map<Integer, QuestionDTO> questionMap = new LinkedHashMap<>();

        for (SurveyResultDetailResponse item : flatList) {
            Integer questionId = item.getQuestionId();

            questionMap.computeIfAbsent(questionId, id -> {
                QuestionDTO q = new QuestionDTO();
                q.setQuestionId(id);
                q.setQuestionText(item.getQuestionText());
                q.setType(item.getType());
                return q;
            });

            AnswerDTO answer = new AnswerDTO(
                    item.getAnswerId(),
                    item.getContent()
            );

            questionMap.get(questionId).getAnswers().add(answer);
        }

        dto.setQuestions(new ArrayList<>(questionMap.values()));
        return dto;
    }

    @Override
    public Page<CompletedSurveyForStudent> findRatedSurveyResultsForStudent(Integer userId, Pageable pageable) {
        try {
            return surveyResultRepo.findRatedSurveyResultsForStudent(userId, pageable);
        } catch (Exception ex){
            System.out.println("Lỗi: " + ex.getMessage());
            throw new RuntimeException("Không thể lấy danh sách các bản báo cáo của sinh viên");
        }
    }

    @Override
    public Page<CompletedSurveyForMonitorAndTeacher> findRatedSurveyResultsForMonitorAndStudent(Integer userId, Pageable pageable) {
        try {
            return surveyResultRepo.findRatedSurveyResultsForMonitorAndTeacher(userId, pageable);
        } catch (Exception ex){
            System.out.println("Lỗi: " + ex.getMessage());
            throw new RuntimeException("Không thể lấy danh sách các bản báo cáo của sinh viên");
        }
    }

    @Override
    public SurveyResult get(Integer id) {
        return surveyResultRepo.findById(id).orElse(null);
    }

    @Override
    public SurveyResult deleteSurvey(Integer id) {
        return null;
    }

    private Users loadUser(Integer userId) {
        Users user = basicBeanRemote.find(Users.class, userId);
        if (user == null) {
            throw new RuntimeException("Người dùng không tồn tại với ID: " + userId);
        }
        return user;
    }

    private Users loadEvaluator(Integer evaluatorId) {
        if (evaluatorId == null) return null;
        return basicBeanRemote.find(Users.class, evaluatorId); // null nếu không tồn tại cũng chấp nhận
    }

    private Survey loadSurvey(Integer surveyId) {
        Survey survey = basicBeanRemote.find(Survey.class, surveyId);
        if (survey == null) {
            throw new RuntimeException("Bản báo cáo không tồn tại với ID: " + surveyId);
        }
        return survey;
    }

    private Question loadQuestion(Integer questionId) {
        Question question = basicBeanRemote.find(Question.class, questionId);
        if (question == null) {
            throw new RuntimeException("Câu hỏi không tồn tại với ID: " + questionId);
        }
        return question;
    }

    private Answer resolveAnswer(Question question, UserAnswerDTO userAnswerDTO) {
        if (QuestionType.Essay.toString().equals(question.getType())) {
            String essayContent = userAnswerDTO.getEssayContent();
            if (essayContent == null || essayContent.isBlank()) {
                throw new RuntimeException("Câu trả lời tự luận không được để trống");
            }
            Answer essayAnswer = new Answer();
            essayAnswer.setContent(essayContent);
            essayAnswer.setPoint(null);
            return basicBeanRemote.create(essayAnswer);
        } else {
            Answer answer = basicBeanRemote.find(Answer.class, userAnswerDTO.getAnswerId());
            if (answer == null) {
                throw new RuntimeException("Câu trả lời không tồn tại với ID: " + userAnswerDTO.getAnswerId());
            }
            return answer;
        }
    }

}
